package com.example.concertReservation.domain.seat;

import com.example.concertReservation.domain.common.BaseTimeEntity;
import com.example.concertReservation.domain.common.SeatGrade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatGrade grade;

    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    private LocalDateTime selectedAt;

    private Long concertId;


    public void release() {
        if (status.isSelected()) {
            this.status = SeatStatus.AVAILABLE;
            this.selectedAt = null;
        }
    }

    public void select() {
        if (status.isAvailable()) {
            this.status = SeatStatus.SELECTED;
            this.selectedAt = LocalDateTime.now();
        }
    }

    public void reserved() {
        this.status = SeatStatus.RESERVED;
    }

    public Seat(String seatNumber, SeatGrade grade, Long price, Long concertId) {
        this.seatNumber = seatNumber;
        this.grade = grade;
        this.price = price;
        this.status = SeatStatus.AVAILABLE;
        this.concertId = concertId;
    }

    public static Seat creatSeat(String seatNumber, SeatGrade grade, Long price, Long concertId) {
        return new Seat(seatNumber, grade, price, concertId);
    }
}


