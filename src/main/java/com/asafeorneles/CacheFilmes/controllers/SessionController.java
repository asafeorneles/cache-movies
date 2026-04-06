package com.asafeorneles.CacheFilmes.controllers;

import com.asafeorneles.CacheFilmes.dtos.SeatResponse;
import com.asafeorneles.CacheFilmes.dtos.SessionRequest;
import com.asafeorneles.CacheFilmes.dtos.SessionResponse;
import com.asafeorneles.CacheFilmes.entities.Seat;
import com.asafeorneles.CacheFilmes.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/sessions")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionResponse> create(@RequestBody SessionRequest sessionRequest){
        SessionResponse session = sessionService.create(sessionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(session);
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> listAll(){
        List<SessionResponse> sessions = sessionService.listAll();
        return ResponseEntity.status(HttpStatus.CREATED).body(sessions);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<SessionResponse> listById(@PathVariable(name = "id") UUID id){
        SessionResponse sessionResponse = sessionService.listById(id);
        return ResponseEntity.status(HttpStatus.OK).body(sessionResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") UUID id){
        sessionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
