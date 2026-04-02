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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final MovieRepository movieRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public SessionResponse create(SessionRequest sessionRequest) {

        // Verificar se a data de inicio da sessão é depois de hoje
        // Verificar se a hora de término da sessão é maior do que o início + a duração do filme...
        // regra pra evitar duas sessões na mesma sala e no mesmo horário

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

        return new SessionResponse(
                session.getSessionId(),
                movie.getMovieId(),
                movie.getName(),
                room.getRoomId(),
                room.getName(),
                session.getStartTime(),
                session.getEndTime(),
                session.getSessionType(),
                session.getSessionFormat()
        );
    }

    public List<SessionResponse> listAll() {
        return sessionRepository.findAll()
                .stream()
                .map(session -> new SessionResponse(
                                session.getSessionId(),
                                session.getMovie().getMovieId(),
                                session.getMovie().getName(),
                                session.getRoom().getRoomId(),
                                session.getRoom().getName(),
                                session.getStartTime(),
                                session.getEndTime(),
                                session.getSessionType(),
                                session.getSessionFormat()
                        )
                )
                .toList();
    }

    public void delete(UUID id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        sessionRepository.delete(session);
    }
}
