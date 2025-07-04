package com.example.concertReservation.infrastructure.external.payment;

import com.example.concertReservation.domain.payment.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TossPaymentsAdapter implements PaymentGatewayPort {

    @Override
    public PaymentResult payment(PaymentGateRequest request) {
        return new PaymentResult(LocalDateTime.now(), true);
    }

    @Override
    public CancelResult cancel(CancelGateRequest request) {
        return new CancelResult();
    }
}
