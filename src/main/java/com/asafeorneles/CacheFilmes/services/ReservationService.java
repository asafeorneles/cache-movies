package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.dtos.ReservationRequest;
import com.asafeorneles.CacheFilmes.dtos.ReservationResponse;
import com.asafeorneles.CacheFilmes.dtos.SessionResponse;
import com.asafeorneles.CacheFilmes.entities.*;
import com.asafeorneles.CacheFilmes.enums.ReservationStatus;
import com.asafeorneles.CacheFilmes.repositories.ReservationRepository;
import com.asafeorneles.CacheFilmes.repositories.SeatRepository;
import com.asafeorneles.CacheFilmes.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Room room = session.getRoom();

        // Verificar se a sessão está ativa (Criar um atributo de atividade pra filtrar as sessões ativas em memória ou no db)...

        // Pega as seats que o usuário deseja pelo nome passado.
        List<Seat> seats = getSeatsByName(room, reservationRequest.seatsName(), seatRepository);

        Reservation reservation = Reservation.builder()
                .session(session)
                .status(ReservationStatus.PENDING)
                .reservationDate(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();

        reservationRepository.save(reservation);

        // Marcar como reservado (temporariamente)... Só depois do pagamento liberar

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

    private static List<Seat> getSeatsByName(Room room, List<String> seatsName, SeatRepository seatRepository){
        List<Seat> seatsByName = new ArrayList<>();
        for(String seatName : seatsName){
            // Mais seguro o back end obter pelo numero da coluna e da linha
            int rowNumber = SeatMapper.getRowNumber(seatName);
            int columnNumber = SeatMapper.getColumnNumber(seatName);
            Seat seat = seatRepository.findByRowNumberAndColumnNumberAndRoom_RoomId(rowNumber, columnNumber, room.getRoomId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            seatsByName.add(seat);
        }
        return seatsByName;
    }

}
