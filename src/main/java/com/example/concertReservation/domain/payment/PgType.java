package com.example.concertReservation.domain.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PgType {
    TOSS("토스페이먼츠"),
    KAKAO_PAY("카카오페이"),
    INICIS("이니시스"),
    NAVER_PAY("네이버페이");
    private final String description;
}
