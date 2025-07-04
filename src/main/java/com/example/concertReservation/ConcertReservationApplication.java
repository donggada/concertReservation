package com.example.concertReservation;

import com.example.concertReservation.domain.common.SeatGrade;
import com.example.concertReservation.domain.concert.Concert;
import com.example.concertReservation.domain.concert.ConcertRepository;
import com.example.concertReservation.domain.concert.SeatPrice;
import com.example.concertReservation.domain.seat.Seat;
import com.example.concertReservation.domain.seat.SeatRepository;
import com.example.concertReservation.domain.seat.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class ConcertReservationApplication implements ApplicationRunner {
	public static void main(String[] args) {
		SpringApplication.run(ConcertReservationApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args)  {
	}
}
