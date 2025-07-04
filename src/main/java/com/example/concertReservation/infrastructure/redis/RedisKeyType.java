package com.example.concertReservation.infrastructure.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RedisKeyType {
    CONCERT_QUEUE_WAIT_KEY ("concert:%d:waiting-queue","콘서트별 대기열"),
    CONCERT_QUEUE_WAIT_TOKEN_KEY ("concert-waiting:%d:token:%d","대가열 토큰(concertId, memberId)"),
    SEAT_LOCK_KEY("seat:lock:%d", "좌석 락 (값: seatId)")
    ;

    private final String format;
    private final String description;

    public String getKey(Object... args) {
        return String.format(this.format, args);
    }
}
