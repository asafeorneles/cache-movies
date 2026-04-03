package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.entities.Reservation;
import com.asafeorneles.CacheFilmes.entities.Seat;
import com.asafeorneles.CacheFilmes.entities.SeatReservation;
import com.asafeorneles.CacheFilmes.entities.Session;
import com.asafeorneles.CacheFilmes.enums.SeatStatus;
import com.asafeorneles.CacheFilmes.repositories.SeatReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatReservationService {

    private final SeatReservationRepository seatReservationRepository;

    public List<SeatReservation> create(List<Seat> seats, Session session, Reservation reservation){

        for (Seat seat : seats){

            boolean alreadyExist = seatReservationRepository.existsBySeatAndSessionAndStatus(seat, session, SeatStatus.RESERVED);

            if (alreadyExist){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Assento já está reservado");
            }

        }
        List<SeatReservation> seatReservations = new ArrayList<>();

        for (Seat seat : seats){
            SeatReservation seatReservation = SeatReservation.builder()
                    .reservation(reservation)
                    .seat(seat)
                    .session(session)
                    .status(SeatStatus.RESERVED)
                    .build();
            seatReservations.add(seatReservation);
        }
        seatReservationRepository.saveAll(seatReservations);
        return seatReservations;
    }
}
