package com.asafeorneles.CacheFilmes.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"room_id", "row", "column"})
})
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "seat_id")
    private UUID seatId;

    @Column(name = "seat_name")
    private String seatName;
    private String row;
    private String column;

    @ManyToOne
    @JoinColumn(name = "room_dd")
    private Room room;
}
