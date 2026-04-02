package com.asafeorneles.CacheFilmes.dtos;

import com.asafeorneles.CacheFilmes.enums.MovieAgeRating;
import com.asafeorneles.CacheFilmes.enums.MovieGenre;

public record MovieRequest (
        String name,
        Integer duration,
        MovieGenre movieGenre,
        MovieAgeRating movieAgeRating
) {
}
