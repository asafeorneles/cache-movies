package com.asafeorneles.CacheFilmes.entities;

import com.asafeorneles.CacheFilmes.enums.SeatStatus;
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
@Table(name = "tb_seat_reservations")
public class SeatReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "seat_reservation_id")
    private UUID seatReservationId;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;
}
