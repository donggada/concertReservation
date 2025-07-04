package com.example.concertReservation.domain.common.event;

public record CreatePaymentEvent(
        Long reservationId,
        Long paymentId,
        Long amount
) {
}
