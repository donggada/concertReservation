package com.example.concertReservation.application.member;


import com.example.concertReservation.domain.member.Member;
import com.example.concertReservation.domain.member.MemberService;
import com.example.concertReservation.infrastructure.security.JwtTokenProvider;
import com.example.concertReservation.application.member.dto.LoginRequest;
import com.example.concertReservation.application.member.dto.MemberSignupRequest;
import com.example.concertReservation.application.member.dto.LoginResponse;
import com.example.concertReservation.application.member.dto.MemberSignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_PASSWORD;


@Service
@RequiredArgsConstructor
public class MemberApplicationService {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberSignupResponse registerMember (MemberSignupRequest request) {
        return MemberSignupResponse.of(memberService.saveMember(request, passwordEncoder.encode(request.password())));
    }

    public LoginResponse loginMember(LoginRequest request) {
        String loginId = request.loginId();
        Member member = memberService.findByLoginId(loginId);

        if (!passwordEncoder.matches(request.password(), member.getPassword())) {
            throw INVALID_PASSWORD.build();
        }

        return LoginResponse.of(loginId, jwtTokenProvider.generateToken(loginId));
    }

}
