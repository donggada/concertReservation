package com.example.concertReservation.application.reservation.dto;

import java.util.List;

public record CreateReservationRequest(
        Long concertId,
        Long totalAmount,
        List<ReservationSeatRequest> seatRequestList
) {
}
