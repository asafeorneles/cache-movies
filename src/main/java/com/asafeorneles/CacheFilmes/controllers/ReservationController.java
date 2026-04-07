package com.asafeorneles.CacheFilmes.controllers;

import com.asafeorneles.CacheFilmes.dtos.ReservationRequest;
import com.asafeorneles.CacheFilmes.dtos.ReservationResponse;
import com.asafeorneles.CacheFilmes.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@RequestBody ReservationRequest reservationRequest){
        ReservationResponse reservationResponse = reservationService.create(reservationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> listAll(){
        List<ReservationResponse> reservationsResponse = reservationService.listAll();
        return ResponseEntity.status(HttpStatus.OK).body(reservationsResponse);
    }
}
