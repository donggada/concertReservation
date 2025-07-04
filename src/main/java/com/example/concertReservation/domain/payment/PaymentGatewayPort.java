package com.example.concertReservation.domain.payment;

public interface PaymentGatewayPort {
    PaymentResult payment(PaymentGateRequest request);
    CancelResult cancel(CancelGateRequest request);
}
