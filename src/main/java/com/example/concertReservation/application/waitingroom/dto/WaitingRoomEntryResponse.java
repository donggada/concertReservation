package com.example.concertReservation.application.waitingroom.dto;

public record WaitingRoomEntryResponse(
        long rank,
        long waitingTime,
        boolean isEnter
) {
    public static WaitingRoomEntryResponse of (long rank) {
        return new WaitingRoomEntryResponse(rank, estimateWaitingTime(rank), rank == 0);
    }

    private static long estimateWaitingTime(long rank) {
        double admissionRatePerSecond = 10.0 / 5.0;
        return (long) Math.ceil(rank / admissionRatePerSecond);

    }
}
