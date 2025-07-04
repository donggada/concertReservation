package com.example.concertReservation.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_LOGIN_ID(HttpStatus.CONFLICT, "존재 하지 않는 아이디 입니다.", "login_id: %s"),
    INVALID_MEMBER(HttpStatus.CONFLICT, "존재하지 않는 유저입니다.", "member_id: %s"),
    MEMBER_NOT_FOUND(CONFLICT, "없는 회원 입니다.", "login_id: %s"),
    DUPLICATE_LOGIN_ID(CONFLICT, "이미 존재한 아이디 입니다.", "login_id: %s"),
    DUPLICATE_ENTER(CONFLICT, "이미 입장된 아이디 입니다.", "member_id: %d"),
    INVALID_PASSWORD(CONFLICT, "비번 확인해주세요.","비번이 일치하지 않음"),
    UNAUTHORIZED_ACCESS(UNAUTHORIZED, "회원 인증 실패입니다.","인증되지 않은 접근 입니다."),
    INVALID_CONCERT(HttpStatus.CONFLICT, "콘서트를 찾을 수 없습니다.", "concert_id: %d"),
    INVALID_SEAT(HttpStatus.CONFLICT, "좌석을 찾을 수 없습니다.", "seat_id: %d"),
    INVALID_RESERVATION(HttpStatus.CONFLICT, "예약을 찾을 수 없습니다.", "reservation_id: %d"),
    SEAT_ALREADY_SELECTED(HttpStatus.CONFLICT, "이미 선택된 좌석입니다.", "seat_id: %d, member_id: %d"),
    SEAT_LOCK_NOT_HELD(HttpStatus.CONFLICT, "좌석이 현재 회원에 의해 잠겨있지 않습니다.", "seat_id: %d, member_id: %d"),
    INVALID_PAYMENT(HttpStatus.CONFLICT, "결제내역을 찾을 수 없습니다.", "payment_id: %d"),
    INVALID_SERVICE(HttpStatus.CONFLICT, "서비스 준비중 입니다.", "type: %s"),
    ;


    private final HttpStatus httpStatus;
    private final String message;
    private final String reason;

    public ApplicationException build(Object ...args) {
        return new ApplicationException(httpStatus, message, reason.formatted(args));
    }
}
