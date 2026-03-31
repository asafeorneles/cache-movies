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
@Table(name = "tb_seatReservations")
public class SeatReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "seat_reservation_id")
    private UUID seatReservationId;

    @ManyToOne
    private Reservation reservation;

    @ManyToOne
    Seat seat;
}
