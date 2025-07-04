package com.example.concertReservation.domain.member;


import com.example.concertReservation.application.member.dto.MemberSignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member saveMember(MemberSignupRequest request, String encodePassword) {
        validateNewMember(request);
        return memberRepository.save(Member.createMember(request, encodePassword));
    }

    public Member findMember(Long memberId) {
        return findByMemberId(memberId);
    }

    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> INVALID_LOGIN_ID.build(loginId));
    }

    public void validateNewMember(MemberSignupRequest request) {
        if (memberRepository.existsByLoginId(request.loginId())) {
            throw DUPLICATE_LOGIN_ID.build(request.loginId());
        }
    }


    private Member findByMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> INVALID_MEMBER.build(memberId));
    }
}
