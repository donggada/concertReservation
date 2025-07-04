package com.example.concertReservation.infrastructure.security;


import com.example.concertReservation.api.member.MemberServiceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberServiceApi memberServiceApi;

    @Override
    public UserDetails loadUserByUsername(String loginId) {
        return new CustomUserDetails(
                memberServiceApi.findTokenCheckByLoginId(loginId)
        );
    }
}

