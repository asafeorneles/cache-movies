package com.asafeorneles.CacheFilmes.dtos;

import com.asafeorneles.CacheFilmes.enums.ReservationStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservationResponse(
        UUID reservationId,
        SessionResponse session,
        List<String> seatsName,
        ReservationStatus status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime reservationDate,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime expiresAt
) {
}
