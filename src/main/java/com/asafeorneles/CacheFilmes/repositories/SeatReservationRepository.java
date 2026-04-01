package com.asafeorneles.CacheFilmes.repositories;

import com.asafeorneles.CacheFilmes.entities.SeatReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SeatReservationRepository extends JpaRepository<SeatReservation, UUID> {
}
