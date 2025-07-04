package com.example.concertReservation.application.reservation.dto;

public record ReservationResponse(
        String concertTitle,
        Long  amount
) {
}
