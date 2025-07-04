package com.example.concertReservation.api.member;


import com.example.concertReservation.domain.member.Member;

public interface MemberServiceApi {
    Member findMember(Long memberId);
    Member findTokenCheckByLoginId(String loginId);
}
