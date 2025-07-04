package com.example.concertReservation.application.member.dto;

public record LoginResponse(String loginId, String token, String message) {

    public static LoginResponse of (String loginId, String token) {
        return new LoginResponse(loginId, token, "로그인 성공");
    }
}
