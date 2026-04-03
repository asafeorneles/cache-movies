package com.asafeorneles.CacheFilmes.controllers;

import com.asafeorneles.CacheFilmes.dtos.SeatDetailsResponse;
import com.asafeorneles.CacheFilmes.entities.Seat;
import com.asafeorneles.CacheFilmes.services.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<SeatDetailsResponse>> listAllByRoom(@RequestParam UUID roomId){
        List<SeatDetailsResponse> seats = seatService.listAllByRoom(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(seats);
    }
}
