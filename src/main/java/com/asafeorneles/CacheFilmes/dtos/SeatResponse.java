package com.asafeorneles.CacheFilmes.dtos;

import java.util.UUID;

public record SeatResponse(
        UUID seatId,
        String name,
        int rowNumber,
        int columnNumber
) {
}
