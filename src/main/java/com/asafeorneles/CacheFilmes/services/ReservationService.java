package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.dtos.ReservationRequest;
import com.asafeorneles.CacheFilmes.dtos.ReservationResponse;
import com.asafeorneles.CacheFilmes.dtos.SessionResponse;
import com.asafeorneles.CacheFilmes.entities.*;
import com.asafeorneles.CacheFilmes.enums.ReservationStatus;
import com.asafeorneles.CacheFilmes.exeptions.ResourceNotFoundExceptions;
import com.asafeorneles.CacheFilmes.repositories.ReservationRepository;
import com.asafeorneles.CacheFilmes.repositories.SeatRepository;
import com.asafeorneles.CacheFilmes.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SessionRepository sessionRepository;
    private final SeatRepository seatRepository;
    private final SeatReservationService seatReservationService;

    @Transactional
    public ReservationResponse create(ReservationRequest reservationRequest) {
        Session session = sessionRepository.findById(reservationRequest.sessionId())
                .orElseThrow(() -> new ResourceNotFoundExceptions(
                        "Session not found by this id: " + reservationRequest.sessionId(), null));

        Room room = session.getRoom();

        // Pega as seats que o usuário deseja pelo nome passado.
        List<Seat> seats = getSeatsByName(room, reservationRequest.seatsName(), seatRepository);

        Reservation reservation = Reservation.builder()
                .session(session)
                .status(ReservationStatus.PENDING)
                .reservationDate(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        reservationRepository.save(reservation);

        List<SeatReservation> seatReservations = seatReservationService.makeReservation(seats, session, reservation);

        reservation.setSeatReservations(seatReservations);

        SessionResponse sessionResponse = new SessionResponse(
                session.getSessionId(),
                session.getMovie().getMovieId(),
                session.getMovie().getName(),
                room.getRoomId(),
                room.getName(),
                session.getStartTime(),
                session.getEndTime(),
                session.getSessionType(),
                session.getSessionFormat().getFormat()
        );

        return new ReservationResponse(
                reservation.getReservationId(),
                sessionResponse,
                reservationRequest.seatsName(),
                reservation.getStatus(),
                null,
                reservation.getExpiresAt()
        );

    }

    private static List<Seat> getSeatsByName(Room room, List<String> seatsName, SeatRepository seatRepository) {
        List<Seat> seatsByName = new ArrayList<>();
        for (String seatName : seatsName) {
            // Mais seguro o back end obter pelo numero da coluna e da linha
            int rowNumber = SeatMapper.getRowNumber(seatName);
            int columnNumber = SeatMapper.getColumnNumber(seatName);
            Seat seat = seatRepository.findByRowNumberAndColumnNumberAndRoom_RoomId(rowNumber, columnNumber, room.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundExceptions(
                            "Seat not found or not exist. Seat name: " + seatName, null));
            seatsByName.add(seat);
        }
        return seatsByName;
    }

    public List<ReservationResponse> listAll() {
        return reservationRepository.findAll()
                .stream()
                .map(reservation -> new ReservationResponse(
                        reservation.getReservationId(),
                        new SessionResponse(
                                reservation.getSession().getSessionId(),
                                reservation.getSession().getMovie().getMovieId(),
                                reservation.getSession().getMovie().getName(),
                                reservation.getSession().getRoom().getRoomId(),
                                reservation.getSession().getRoom().getName(),
                                reservation.getSession().getStartTime(),
                                reservation.getSession().getEndTime(),
                                reservation.getSession().getSessionType(),
                                reservation.getSession().getSessionFormat().getFormat()
                        ),
                        reservation.getSeatReservations().stream().map(sr -> sr.getSeat().getSeatName()).toList(),
                        reservation.getStatus(),
                        reservation.getReservationDate(),
                        reservation.getExpiresAt()
                ))
                .sorted(Comparator.comparing(rs -> rs.session().movieName()))
                .toList();
    }
}
