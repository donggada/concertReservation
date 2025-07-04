package com.example.concertReservation.application.seat.dto;

import java.util.List;

public record SeatSelectionRequest(
        List<Long> seatIdList
) {
}
