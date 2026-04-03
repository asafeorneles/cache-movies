package com.asafeorneles.CacheFilmes.dtos;

import java.util.UUID;

public record SeatDetailsResponse(
        UUID seatId,
        String name,
        UUID roomId,
        int rowNumber,
        int columnNumber

) {
}
