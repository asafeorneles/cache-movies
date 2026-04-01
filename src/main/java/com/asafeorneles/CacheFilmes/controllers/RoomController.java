package com.asafeorneles.CacheFilmes.controllers;

import com.asafeorneles.CacheFilmes.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


}
