package com.example.concertReservation.domain.common.event;

public record CompletedPaymentEvent(
        Long reservationId,
        Long paymentId
) {
}
