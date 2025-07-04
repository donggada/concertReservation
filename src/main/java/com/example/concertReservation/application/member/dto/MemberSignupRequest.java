package com.example.concertReservation.application.member.dto;

import com.example.concertReservation.domain.member.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberSignupRequest(
        @NotBlank(message = "사용자 이름은 필수 항목입니다.")
        String username,

        @NotBlank(message = "비밀번호 은 필수 항목입니다.")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @NotBlank(message = "아이디는 필수 항목입니다.")
        String loginId,

        @NotBlank(message = "롤은 필수 항목입니다.")
        MemberRole role
) {
}
