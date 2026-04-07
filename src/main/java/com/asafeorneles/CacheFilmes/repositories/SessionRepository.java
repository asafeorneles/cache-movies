package com.asafeorneles.CacheFilmes.repositories;

import com.asafeorneles.CacheFilmes.entities.Room;
import com.asafeorneles.CacheFilmes.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    @Query("""
                SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
                FROM Session s
                WHERE s.room = :room
                AND (
                    :startTime < s.endTime
                    AND :endTime > s.startTime
                )
            """)
    boolean existsConflictingSession(
            @Param("room") Room room,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
