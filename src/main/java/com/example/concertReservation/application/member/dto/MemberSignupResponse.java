package com.example.concertReservation.application.member.dto;


import com.example.concertReservation.domain.member.Member;

public record MemberSignupResponse(
        Long id,
        String username,
        String message
) {

    public static MemberSignupResponse of (Member member) {
        return new MemberSignupResponse(
                member.getId(),
                member.getUsername(),
                "회원가입 성공"
        );
    }

}
