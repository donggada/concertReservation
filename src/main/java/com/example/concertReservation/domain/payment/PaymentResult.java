package com.example.concertReservation.domain.payment;

import java.time.LocalDateTime;

public record PaymentResult(
        LocalDateTime paidAt,
        boolean isSuccess
) {
}
