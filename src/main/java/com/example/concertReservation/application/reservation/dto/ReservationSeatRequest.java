package com.example.concertReservation.application.reservation.dto;

import com.example.concertReservation.domain.common.SeatGrade;

public record ReservationSeatRequest(
        Long seatId,
        String seatNumber,
        SeatGrade grade,
        Long price
) {
}
