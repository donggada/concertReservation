package com.example.concertReservation.domain.payment;

import com.example.concertReservation.application.payment.dto.PaymentRequest;
import com.example.concertReservation.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "pg_type", nullable = false)
    private PgType pgType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    private LocalDateTime paidAt;

    private Long reservationId;

    public void cancel() {
        status = PaymentStatus.CANCELED;
    }

    public Payment(Long amount, PgType pgType, PaymentMethod method, PaymentStatus status, LocalDateTime paidAt, Long reservationId) {
        this.amount = amount;
        this.pgType = pgType;
        this.method = method;
        this.status = status;
        this.paidAt = paidAt;
        this.reservationId = reservationId;
    }

    public static Payment createPayment (PaymentRequest request, PaymentResult paymentResult) {
        return new Payment(request.amount(), request.pgType(), request.method(), paymentResult.isSuccess() ? PaymentStatus.COMPLETED : PaymentStatus.FAILED, paymentResult.paidAt(), request.reservationId());
    }
}
