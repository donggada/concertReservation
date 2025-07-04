package com.example.concertReservation.infrastructure.external.member;


import com.example.concertReservation.api.member.MemberServiceApi;
import com.example.concertReservation.domain.member.Member;
import com.example.concertReservation.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_MEMBER;
import static com.example.concertReservation.infrastructure.exception.ErrorCode.MEMBER_NOT_FOUND;


@Service
@RequiredArgsConstructor
public class MemberServiceApiJpa implements MemberServiceApi {
    private final MemberRepository memberRepository;

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> INVALID_MEMBER.build(memberId));
    }

    @Override
    public Member findTokenCheckByLoginId(String loginId) {
        return memberRepository.findTokenCheckByLoginId(loginId)
                .orElseThrow(() -> MEMBER_NOT_FOUND.build(loginId));
    }
}
