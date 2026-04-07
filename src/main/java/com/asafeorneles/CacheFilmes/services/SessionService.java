package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.dtos.SeatReservationResponse;
import com.asafeorneles.CacheFilmes.dtos.SessionRequest;
import com.asafeorneles.CacheFilmes.dtos.SessionResponse;
import com.asafeorneles.CacheFilmes.entities.*;
import com.asafeorneles.CacheFilmes.exeptions.ConflictBusinessException;
import com.asafeorneles.CacheFilmes.exeptions.ResourceNotFoundExceptions;
import com.asafeorneles.CacheFilmes.repositories.MovieRepository;
import com.asafeorneles.CacheFilmes.repositories.RoomRepository;
import com.asafeorneles.CacheFilmes.repositories.SeatReservationRepository;
import com.asafeorneles.CacheFilmes.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;
    private final SeatReservationService seatReservationService;
    private final SeatReservationRepository seatReservationRepository;

    @Transactional
    public SessionResponse create(SessionRequest sessionRequest) {

        Movie movie = movieRepository.findById(sessionRequest.movieId())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "Movie not found by this id: " + sessionRequest.movieId(), null));

        Room room = roomRepository.findById(sessionRequest.roomId())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "Room not found by this id: " + sessionRequest.roomId(), null));

        verifications(sessionRequest, movie, room, sessionRepository);

        Session session = Session.builder()
                .movie(movie)
                .room(room)
                .startTime(sessionRequest.startTime())
                .endTime(sessionRequest.endTime())
                .sessionType(sessionRequest.sessionType())
                .sessionFormat(sessionRequest.sessionFormat())
                .build();

        sessionRepository.save(session);

        // Pega os assentos da sala da sessão
        List<Seat> seats = room.getSeats();

        // Cria as SeatReservation
        List<SeatReservation> seatReservations = seatReservationService.create(seats, session);

        // Seta as SeatReservation na sessão criada
        session.setSeatReservations(seatReservations);

        return new SessionResponse(
                session.getSessionId(),
                movie.getMovieId(),
                movie.getName(),
                room.getRoomId(),
                room.getName(),
                session.getStartTime(),
                session.getEndTime(),
                session.getSessionType(),
                session.getSessionFormat().getFormat()
        );
    }

    private static void verifications(SessionRequest sessionRequest, Movie movie, Room room, SessionRepository sessionRepository){
        // Talvez marcar a sessão como finalizada e criar métodos para verificar isso...

        if (sessionRequest.startTime().isBefore(LocalDateTime.now())){
            throw new ConflictBusinessException("The start date cannot be earlier than today.", null);
        }

        if (sessionRequest.endTime().isBefore(sessionRequest.startTime().plusMinutes(movie.getDuration()))){
            throw new ConflictBusinessException("The end date cannot be earlier than movie duration.", null);
        }

        if (sessionRepository.existsConflictingSession(room, sessionRequest.startTime(), sessionRequest.endTime())){
            throw new ConflictBusinessException("There is already a session in this room at this time.", null);
        }
    }

    public List<SessionResponse> listAll() {
        return sessionRepository.findAll()
                .stream()
                .map(session -> new SessionResponse(
                                session.getSessionId(),
                                session.getMovie().getMovieId(),
                                session.getMovie().getName(),
                                session.getRoom().getRoomId(),
                                session.getRoom().getName(),
                                session.getStartTime(),
                                session.getEndTime(),
                                session.getSessionType(),
                                session.getSessionFormat().getFormat()
                        )
                )
                .toList();
    }

    public void delete(UUID id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "Session not found by this id: " + id, null));
        sessionRepository.delete(session);
    }

    public SessionResponse listById(UUID id) {
        return sessionRepository.findById(id)
                .map(session -> new SessionResponse(
                        session.getSessionId(),
                        session.getMovie().getMovieId(),
                        session.getMovie().getName(),
                        session.getRoom().getRoomId(),
                        session.getRoom().getName(),
                        session.getStartTime(),
                        session.getEndTime(),
                        session.getSessionType(),
                        session.getSessionFormat().getFormat()))
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "Session not found by this id: " + id, null));

    }

    public List<SeatReservationResponse> listSeatsByRoom(UUID id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "Session not found by this id: " + id, null));
        return seatReservationRepository.findBySession(session)
                .stream()
                .map(sr -> new SeatReservationResponse(
                        sr.getSeat().getSeatId(),
                        sr.getSeat().getSeatName(),
                        sr.getSeat().getRowNumber(),
                        sr.getSeat().getColumnNumber(),
                        sr.getSeat().getRoom().getRoomId(),
                        session.getSessionId(),
                        sr.getStatus().name()
                ))
                .sorted(Comparator.comparing(SeatReservationResponse::name))
                .toList();
    }
}
