package com.example.concertReservation.domain.seat;

import org.springframework.stereotype.Service;

@Service
public interface SeatLockService {
    boolean lockSeat(Long seatId, Long memberId);
    boolean releaseSeatLock(Long seatId);
    boolean isLockedBy(Long seatId, Long memberId);
}
