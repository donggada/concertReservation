package com.example.concertReservation.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentMethod {
    CREDIT_CARD("신용카드"),
    BANK_TRANSFER("계좌이체"),
    MOBILE_PAYMENT("모바일결제"),
    VIRTUAL_ACCOUNT("가상계좌");

    private final String description;
}
