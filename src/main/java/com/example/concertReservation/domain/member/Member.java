package com.example.concertReservation.domain.member;


import com.example.concertReservation.domain.common.BaseTimeEntity;
import com.example.concertReservation.application.member.dto.MemberSignupRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@BatchSize(size = 100)
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    private Member(String loginId, String password, String username, MemberRole role) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public static Member createMember(MemberSignupRequest request, String encodePassword) {
        return new Member(request.loginId(), encodePassword, request.username(), request.role());
    }
}
