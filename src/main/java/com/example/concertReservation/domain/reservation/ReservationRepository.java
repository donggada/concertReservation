package com.example.concertReservation.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStatusAndCreatedDateBefore(ReservationStatus status, LocalDateTime currentTime);
}
