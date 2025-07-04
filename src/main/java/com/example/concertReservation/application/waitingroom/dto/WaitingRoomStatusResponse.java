package com.example.concertReservation.application.waitingroom.dto;

public record WaitingRoomStatusResponse(
        long rank,
        long waitingTime
) {
}
