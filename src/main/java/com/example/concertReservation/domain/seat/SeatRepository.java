package com.example.concertReservation.domain.seat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface  SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByStatusAndSelectedAtBefore (SeatStatus status, LocalDateTime time) ;
}
