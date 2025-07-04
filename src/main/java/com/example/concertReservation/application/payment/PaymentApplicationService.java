package com.example.concertReservation.application.payment;

import com.example.concertReservation.api.reservation.ReservationServiceApi;
import com.example.concertReservation.application.payment.dto.CancelRequest;
import com.example.concertReservation.application.payment.dto.PaymentRequest;
import com.example.concertReservation.domain.common.event.CancelPaymentEvent;
import com.example.concertReservation.domain.common.event.CreatePaymentEvent;
import com.example.concertReservation.domain.payment.Payment;
import com.example.concertReservation.domain.payment.PaymentGateRequest;
import com.example.concertReservation.domain.payment.PaymentRepository;
import com.example.concertReservation.domain.payment.PaymentResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_PAYMENT;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {
    private final PaymentRepository paymentRepository;
    private final ReservationServiceApi reservationServiceApi;
    private final PaymentGatewayFactory paymentGatewayFactory;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void payment(PaymentRequest request) {
        reservationServiceApi.findReservation(request.reservationId());
        PaymentResult paymentResult = paymentGatewayFactory.getService(request.pgType()).payment(new PaymentGateRequest());
        Payment savePayment = paymentRepository.save(Payment.createPayment(request, paymentResult));

        if (paymentResult.isSuccess()) {
            eventPublisher.publishEvent(new CreatePaymentEvent(request.reservationId(), savePayment.getId(), savePayment.getAmount()));
            return;
        }

        eventPublisher.publishEvent(new CancelPaymentEvent(request.reservationId()));
    }

    @Transactional
    public void cancel(CancelRequest request) {
        Payment payment = paymentRepository.findById(request.paymentId()).orElseThrow(() -> INVALID_PAYMENT.build(request.paymentId()));
        payment.cancel();
        eventPublisher.publishEvent(new CancelPaymentEvent(payment.getId()));
    }
}
