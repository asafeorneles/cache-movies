package com.asafeorneles.CacheFilmes.dtos;

import java.util.List;
import java.util.UUID;

public record ReservationRequest(
        UUID sessionId,
        List<String> seatsName
) {
}
