package com.example.concertReservation.application.payment.dto;

import com.example.concertReservation.domain.payment.PaymentMethod;
import com.example.concertReservation.domain.payment.PgType;

public record PaymentRequest(
        Long amount,
        PgType pgType,
        PaymentMethod method,
        Long reservationId
) {
}
