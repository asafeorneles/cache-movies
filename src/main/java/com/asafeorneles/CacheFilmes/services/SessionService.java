package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.dtos.SessionRequest;
import com.asafeorneles.CacheFilmes.dtos.SessionResponse;
import com.asafeorneles.CacheFilmes.entities.Movie;
import com.asafeorneles.CacheFilmes.entities.Room;
import com.asafeorneles.CacheFilmes.entities.Session;
import com.asafeorneles.CacheFilmes.repositories.MovieRepository;
import com.asafeorneles.CacheFilmes.repositories.RoomRepository;
import com.asafeorneles.CacheFilmes.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    public SessionResponse create(SessionRequest sessionRequest) {

        // Verificar se a data de inicio da sessão é depois de hoje
        // Verificar se a hora de término da sessão é maior do que o início + a duração do filme...

        Movie movie = movieRepository.findById(sessionRequest.movieId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Room room = roomRepository.findById(sessionRequest.roomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Session session = Session.builder()
                .movie(movie)
                .room(room)
                .startTime(sessionRequest.startTime())
                .endTime(sessionRequest.endTime())
                .sessionType(sessionRequest.sessionType())
                .sessionFormat(sessionRequest.sessionFormat())
                .build();

        sessionRepository.save(session);



        return new SessionResponse(session.getMovie().getMovieId(), session.getMovie().getName(),
                session.getRoom().getRoomId(), session.getRoom().getName(), session.getStartTime()
                , session.getEndTime(), session.getSessionType(), session.getSessionFormat());
    }
}
