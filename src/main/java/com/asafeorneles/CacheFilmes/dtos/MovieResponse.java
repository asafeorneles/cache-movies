package com.asafeorneles.CacheFilmes.dtos;

import com.asafeorneles.CacheFilmes.enums.MovieGenre;

import java.util.List;
import java.util.UUID;

public record MovieResponse(
        UUID movieId,
        String name,
        Integer duration,
        MovieGenre movieGenre,
        String  movieAgeRating,
        List<SessionResponse> sessions
) {
}
