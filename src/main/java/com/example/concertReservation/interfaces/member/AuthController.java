package com.example.concertReservation.interfaces.member;



import com.example.concertReservation.application.member.MemberApplicationService;
import com.example.concertReservation.application.member.dto.LoginRequest;
import com.example.concertReservation.application.member.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final MemberApplicationService memberApplicationService;

    @PostMapping("/login")
    public LoginResponse login(@Validated @RequestBody LoginRequest request) {
        return memberApplicationService.loginMember(request);
    }

}
