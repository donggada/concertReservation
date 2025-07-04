package com.example.concertReservation.interfaces;

import com.example.concertReservation.application.member.MemberApplicationService;
import com.example.concertReservation.application.member.dto.MemberSignupRequest;
import com.example.concertReservation.domain.common.SeatGrade;
import com.example.concertReservation.domain.concert.Concert;
import com.example.concertReservation.domain.concert.ConcertRepository;
import com.example.concertReservation.domain.concert.SeatPrice;
import com.example.concertReservation.domain.member.Member;
import com.example.concertReservation.domain.member.MemberRole;
import com.example.concertReservation.domain.seat.Seat;
import com.example.concertReservation.domain.seat.SeatRepository;
import com.example.concertReservation.interfaces.member.MemberController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/mock")
@RequiredArgsConstructor
public class TestMockController {
    private final ConcertRepository concertRepository;
    private final SeatRepository seatRepository;
    private final MemberApplicationService memberApplicationService;

    @PostMapping("/concert")
    public String makeConcert(String title, int seatCount) {
        List<SeatPrice> seatPrice = List.of(
                SeatPrice.createSeatPrice(SeatGrade.VIP, 300000L),
                SeatPrice.createSeatPrice(SeatGrade.S, 150000L),
                SeatPrice.createSeatPrice(SeatGrade.R, 70000L),
                SeatPrice.createSeatPrice(SeatGrade.A, 50000L)
        );

        LocalDateTime now = LocalDateTime.now();

        Concert concert = Concert.createConcert(
                title,
                now.plusDays(2L),
                now.minusDays(1L),
                now.plusDays(1L),
                "고덕 공연장",
                seatCount,
                seatPrice
        );

		concertRepository.save(concert);

        List<Seat> seats = IntStream.range(0, seatCount)
                .mapToObj(i -> {
                    String seatNumber = generateSeatNumber(i);
                    SeatGrade grade;
                    long price;
                    if (i < 100) {
                        grade = SeatGrade.VIP;
                        price = 300000L;
                    } else if (i < 300) {
                        grade = SeatGrade.S;
                        price = 150000L;
                    } else if (i < 600) {
                        grade = SeatGrade.R;
                        price = 70000L;
                    } else {
                        grade = SeatGrade.A;
                        price = 50000L;
                    }
                    return Seat.creatSeat(seatNumber, grade, price, concert.getId());
                })
                .toList();

		seatRepository.saveAll(seats);
        return "성공";
    }

    private String generateSeatNumber(int index) {
        char section = (char) ('A' + (index / 100)); // 100개마다 구역 변경 (A, B, C, ...)
        int number = (index % 100) + 1; // 1부터 시작하는 번호
        return section + "-" + number;
    }

    @GetMapping("/mock2")
    public String makeUser(int userCount) {
        IntStream.rangeClosed(1, userCount)
                .forEach(index -> memberApplicationService.registerMember(
                        new MemberSignupRequest(
                                "test" + index,
                                "password123",
                                "user_" + index,
                                MemberRole.ROLE_USER)
                ));
        return "성공";
    }
}
