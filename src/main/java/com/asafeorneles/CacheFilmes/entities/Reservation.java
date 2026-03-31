package com.asafeorneles.CacheFilmes.entities;

import com.asafeorneles.CacheFilmes.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "reservation_id")
    private UUID reservationId;

    @OneToMany(mappedBy = "reservation")
    private List<SeatReservation> seatReservation;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Column(name = "reservation_date")
    private LocalDateTime reservationDate;
    private LocalDateTime expiresAt; // Tempo até o usuário realizar o pagamento.
}
