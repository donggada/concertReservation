package com.example.concertReservation.domain.common.event;

public record CancelPaymentEvent(
        Long reservationId
) {
}
