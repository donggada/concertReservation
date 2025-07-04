package com.example.concertReservation.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    PENDING("결제 대기", "결제 요청이 생성되었지만 아직 처리되지 않은 상태"),
    PROCESSING("처리중", "PG사에서 결제를 처리 중인 상태"),
    COMPLETED("완료", "결제가 성공적으로 완료된 상태"),
    FAILED("실패", "결제 처리 중 오류가 발생한 상태"),
    CANCELED("취소됨", "완료된 결제가 취소된 상태");

    private final String description;
    private final String detail;

    public boolean isFinal() {
        return this == COMPLETED || this == FAILED || this == CANCELED;
    }
}