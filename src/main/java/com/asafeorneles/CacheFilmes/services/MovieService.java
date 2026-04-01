package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.dtos.MovieRequest;
import com.asafeorneles.CacheFilmes.entities.Movie;
import com.asafeorneles.CacheFilmes.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Movie create(MovieRequest movieRequest){
        Movie movie = Movie.builder()
                .name(movieRequest.name())
                .duration(movieRequest.duration())
                .movieGenre(movieRequest.movieGenre())
                .movieAgeRating(movieRequest.movieAgeRating())
                .build();

        return movieRepository.save(movie);
    }


    public List<Movie> listAll() {
        return movieRepository.findAll();
    }
}
