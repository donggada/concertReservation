package com.example.concertReservation.domain.concert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    @Query("SELECT c FROM Concert c WHERE c.reservationOpenDate <= :currentTime AND c.reservationCloseDate >= :currentTime AND c.closed = false")
    List<Concert> findActiveConcertsForAdmission(LocalDateTime currentTime);

    @Query("SELECT c FROM Concert c WHERE c.reservationCloseDate < :currentTime AND c.closed = false")
    List<Concert> findClosedConcertsNeedingQueueCleanup(LocalDateTime currentTime);
}
