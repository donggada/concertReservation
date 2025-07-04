package com.example.concertReservation.domain.reservation;

import com.example.concertReservation.application.reservation.dto.CreateReservationRequest;
import com.example.concertReservation.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Long concertId;

    private Long paymentId;

    private Long totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;


    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationSeat> seats = new ArrayList<>();

    public void expire() {
        this.status = ReservationStatus.EXPIRED;
    }

    public Reservation(Long memberId, Long concertId, Long totalAmount, ReservationStatus status) {
        this.memberId = memberId;
        this.concertId = concertId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public static Reservation createReservation (CreateReservationRequest request, Long memberId) {
        return new Reservation(memberId, request.concertId(), request.totalAmount(), ReservationStatus.PENDING);
    }

    public void addReservationSeat(List<ReservationSeat> reservationSeatList) {
        seats.addAll(reservationSeatList);
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELED;
    }

    public void completed() {
        this.status = ReservationStatus.COMPLETED;
    }

    public void updatePaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
