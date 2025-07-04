package com.example.concertReservation.domain.seat;

import lombok.Getter;


@Getter
public enum SeatStatus {
    AVAILABLE("예약 가능"),
    SELECTED("선택됨"),
    RESERVED("예약됨"),
    UNAVAILABLE("사용 불가");

    private final String description;

    SeatStatus(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return this == AVAILABLE;
    }

    public boolean isSelected() {
        return this == SELECTED;
    }

}
