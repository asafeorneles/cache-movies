package com.asafeorneles.CacheFilmes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "room_id")
    private UUID roomId;

    @Column(name = "room_name")
    private String roomName;
    private int rows;
    private int columns;
    @OneToMany(mappedBy = "room")
    private List<Seat> seats;
}
