package com.example.concertReservation.interfaces.member;

import com.example.concertReservation.application.member.MemberApplicationService;
import com.example.concertReservation.application.member.dto.MemberSignupRequest;
import com.example.concertReservation.application.member.dto.MemberSignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/member")
public class MemberController {

    private final MemberApplicationService memberApplicationService;

    @PostMapping("")
    public MemberSignupResponse signupMember (@RequestBody MemberSignupRequest request) {
        return memberApplicationService.registerMember(request);
    }
}
