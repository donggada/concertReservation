package com.example.concertReservation.application.payment;

import com.example.concertReservation.domain.common.event.CancelPaymentEvent;
import com.example.concertReservation.domain.common.event.CancelReservationEvent;
import com.example.concertReservation.domain.common.event.CompletedPaymentEvent;
import com.example.concertReservation.domain.common.event.CreatePaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {
    private final ApplicationEventPublisher eventPublisher;


    @EventListener
    public void createPayment(CreatePaymentEvent event) {
        eventPublisher.publishEvent(new CompletedPaymentEvent(event.reservationId(), event.paymentId()));
    }

    @EventListener
    public void cancelPayment(CancelPaymentEvent event) {
        eventPublisher.publishEvent(new CancelReservationEvent(event.reservationId()));
    }
}
