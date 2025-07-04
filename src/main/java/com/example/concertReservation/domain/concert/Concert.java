package com.example.concertReservation.domain.concert;

import com.example.concertReservation.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Concert extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDateTime performanceDate;

    private LocalDateTime reservationOpenDate;

    private LocalDateTime reservationCloseDate;

    private String location;

    private int totalSeats;

    private int availableSeats;

    private boolean closed;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatPrice> seatPrices = new ArrayList<>();

    public boolean isReservationAvailable() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(reservationOpenDate) &&
                now.isBefore(reservationCloseDate);
    }

    public void decrementAvailableSeats() {
        if (availableSeats > 0) {
            availableSeats--;
        }
    }

    public void incrementAvailableSeats() {
        if (availableSeats < totalSeats) {
            availableSeats++;
        }
    }

    public void markClosed() {
        this.closed = true;
    }


    public Concert(String title, LocalDateTime performanceDate, LocalDateTime reservationOpenDate, LocalDateTime reservationCloseDate, String location, int totalSeats, List<SeatPrice> seatPrices) {
        this.title = title;
        this.performanceDate = performanceDate;
        this.reservationOpenDate = reservationOpenDate;
        this.reservationCloseDate = reservationCloseDate;
        this.location = location;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.seatPrices = seatPrices;
    }

    public static Concert createConcert(String title, LocalDateTime performanceDate, LocalDateTime reservationOpenDate, LocalDateTime reservationCloseDate, String location, int totalSeats, List<SeatPrice> seatPrices) {
        return new Concert(title, performanceDate, reservationOpenDate, reservationCloseDate, location, totalSeats ,seatPrices);
    }


}
