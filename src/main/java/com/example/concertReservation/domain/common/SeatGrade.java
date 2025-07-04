package com.example.concertReservation.domain.common;

import lombok.Getter;

@Getter
public enum SeatGrade {
    VIP("VIP석"),
    R("R석" ),
    S("S석"),
    A("A석" );

    private final String description;

    SeatGrade(String description) {
        this.description = description;
    }

}