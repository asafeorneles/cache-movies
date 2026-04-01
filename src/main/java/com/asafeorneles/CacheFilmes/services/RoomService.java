package com.asafeorneles.CacheFilmes.services;

import com.asafeorneles.CacheFilmes.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

}
