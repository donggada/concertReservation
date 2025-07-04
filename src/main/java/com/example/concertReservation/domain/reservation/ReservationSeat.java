package com.example.concertReservation.domain.reservation;

import com.example.concertReservation.application.reservation.dto.CreateReservationRequest;
import com.example.concertReservation.application.reservation.dto.ReservationSeatRequest;
import com.example.concertReservation.domain.common.BaseTimeEntity;
import com.example.concertReservation.domain.common.SeatGrade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationSeat extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private Long seatId;

    @Column(name = "seat_number")
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatGrade grade;

    @Column(nullable = false)
    private Long price;

    public ReservationSeat(Long seatId, String seatNumber, SeatGrade grade, Long price, Reservation reservation) {
        this.seatId = seatId;
        this.reservation = reservation;
        this.seatNumber = seatNumber;
        this.grade = grade;
        this.price = price;
    }

    public static ReservationSeat createReservationSeat (ReservationSeatRequest request, Reservation reservation) {
        return new ReservationSeat(request.seatId(), request.seatNumber(), request.grade(), request.price(), reservation);
    }
}
