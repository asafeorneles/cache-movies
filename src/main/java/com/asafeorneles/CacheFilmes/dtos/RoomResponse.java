package com.asafeorneles.CacheFilmes.dtos;

import java.util.List;
import java.util.UUID;

public record RoomResponse (
        UUID roomId,
        String name,
        List<SeatResponse> seats
){

}
