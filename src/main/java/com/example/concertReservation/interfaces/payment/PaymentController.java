package com.example.concertReservation.interfaces.payment;

import com.example.concertReservation.application.payment.PaymentApplicationService;
import com.example.concertReservation.application.payment.dto.CancelRequest;
import com.example.concertReservation.application.payment.dto.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;

    @PostMapping("/")
    public void payment(@RequestBody PaymentRequest request) {
        paymentApplicationService.payment(request);
    }

    @PostMapping("/cancel")
    public void cancel(@RequestBody CancelRequest request) {
        paymentApplicationService.cancel(request);
    }


}