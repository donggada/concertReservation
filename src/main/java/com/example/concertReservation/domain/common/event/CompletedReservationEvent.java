package com.example.concertReservation.domain.common.event;

public record CompletedReservationEvent(
        Long reservationId
) {
}
