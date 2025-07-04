package com.example.concertReservation.domain.concert;

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
public class SeatPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @Enumerated(EnumType.STRING)
    private SeatGrade grade;

    private Long price;

    public void linkToConcert(Concert concert) {
        this.concert = concert;
    }

    public SeatPrice(SeatGrade grade, Long price) {
        this.grade = grade;
        this.price = price;
    }

    public static SeatPrice createSeatPrice (SeatGrade grade, Long price) {
        return new SeatPrice(grade, price);
    }
}
