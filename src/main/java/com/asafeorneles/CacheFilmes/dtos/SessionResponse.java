package com.asafeorneles.CacheFilmes.dtos;

import com.asafeorneles.CacheFilmes.enums.SessionFormat;
import com.asafeorneles.CacheFilmes.enums.SessionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Struct;
import java.time.LocalDateTime;
import java.util.UUID;

public record SessionResponse(
        UUID movieId,
        String movieName,
        UUID roomId,
        String roomName,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime startTime,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime endTime,
        SessionType sessionType,
        SessionFormat sessionFormat
) {
}
