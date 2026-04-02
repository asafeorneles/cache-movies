package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.dtos.MovieRequest;
import com.asafeorneles.CacheFilmes.entities.Movie;
import com.asafeorneles.CacheFilmes.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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

    public void delete(UUID id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        movieRepository.delete(movie);
    }
}
