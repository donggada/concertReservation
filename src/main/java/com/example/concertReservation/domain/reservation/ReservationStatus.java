package com.example.concertReservation.domain.reservation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    PENDING("대기중"),
    COMPLETED("완료"),
    CANCELED("취소됨"),
    EXPIRED("만료됨");

    private final String description;
}
