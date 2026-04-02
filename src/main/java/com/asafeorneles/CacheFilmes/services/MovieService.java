package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.dtos.MovieRequest;
import com.asafeorneles.CacheFilmes.dtos.MovieResponse;
import com.asafeorneles.CacheFilmes.dtos.SessionResponse;
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

    public MovieResponse create(MovieRequest movieRequest) {
        Movie movie = Movie.builder()
                .name(movieRequest.name())
                .duration(movieRequest.duration())
                .movieGenre(movieRequest.movieGenre())
                .movieAgeRating(movieRequest.movieAgeRating())
                .build();

        movieRepository.save(movie);
        return new MovieResponse(
                movie.getMovieId(),
                movie.getName(),
                movie.getDuration(),
                movie.getMovieGenre(),
                movie.getMovieAgeRating().getAgeRating(),
                null
        );
    }


    public List<MovieResponse> listAll() {
        return movieRepository.findAll()
                .stream()
                .map(movie -> new MovieResponse(
                        movie.getMovieId(),
                        movie.getName(),
                        movie.getDuration(),
                        movie.getMovieGenre(),
                        movie.getMovieAgeRating().getAgeRating(),
                        movie.getSessions()
                                .stream()
                                .map(session -> new SessionResponse(
                                                session.getSessionId(),
                                                movie.getMovieId(),
                                                movie.getName(),
                                                session.getRoom().getRoomId(),
                                                session.getRoom().getName(),
                                                session.getStartTime(),
                                                session.getEndTime(),
                                                session.getSessionType(),
                                                session.getSessionFormat().getFormat()
                                        )
                                )
                                .toList()
                )).toList();
    }

    public void delete(UUID id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        movieRepository.delete(movie);
    }
}
