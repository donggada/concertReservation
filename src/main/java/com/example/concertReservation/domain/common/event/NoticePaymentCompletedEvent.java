package com.example.concertReservation.domain.common.event;

public record NoticePaymentCompletedEvent(
        Long reservationId
) {
}
