package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.dtos.SeatDetailsResponse;
import com.asafeorneles.CacheFilmes.dtos.SeatResponse;
import com.asafeorneles.CacheFilmes.entities.Room;
import com.asafeorneles.CacheFilmes.entities.Seat;
import com.asafeorneles.CacheFilmes.repositories.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    /* private static Map<Integer, String> alphabetMap = Map.ofEntries(
            Map.entry(1, "A"),
            Map.entry(2, "B"),
            Map.entry(3, "C"),
            Map.entry(4, "D"),
            Map.entry(5, "E"),
            Map.entry(6, "F"),
            Map.entry(7, "G"),
            Map.entry(8, "H"),
            Map.entry(9, "I"),
            Map.entry(10, "J"),
            Map.entry(11, "K"),
            Map.entry(12, "L"),
            Map.entry(13, "M"),
            Map.entry(14, "N"),
            Map.entry(15, "O"),
            Map.entry(16, "P"),
            Map.entry(17, "Q"),
            Map.entry(18, "R"),
            Map.entry(19, "S"),
            Map.entry(20, "T"),
            Map.entry(21, "U"),
            Map.entry(22, "V"),
            Map.entry(23, "W"),
            Map.entry(24, "X"),
            Map.entry(25, "Y"),
            Map.entry(26, "Z")
    ); */

    public void createAndSetSeats(Room room) {
        List<Seat> seats = new ArrayList<>();

        for (int i = 1; i <= room.getRowsQuantity(); i++) {
            for (int j = 1; j <= room.getColumnsQuantity(); j++) {
                Seat seat = Seat.builder()
                        .room(room)
                        .rowNumber(i)
                        .columnNumber(j)
                        .build();

                seats.add(seat);
            }
        }

        seats.forEach(seat -> seat.setName(seat.getSeatName()));
        room.setSeats(seats);
    }

    public List<SeatResponse> createSeatsResponse(List<Seat> seats) {


        return seats.stream()
                .map(seat -> new SeatResponse(seat.getSeatName()))
                .sorted(Comparator.comparing(SeatResponse::name))
                .toList();
    }

    public List<SeatDetailsResponse> listAllByRoom(UUID roomId) {
        return seatRepository.findByRoom_RoomId(roomId)
                .stream()
                .map(seat -> new SeatDetailsResponse(seat.getSeatId(), seat.getSeatName(), roomId, seat.getRowNumber(), seat.getColumnNumber()))
                .sorted(Comparator.comparing(SeatDetailsResponse::name))
                .toList();
    }
}
