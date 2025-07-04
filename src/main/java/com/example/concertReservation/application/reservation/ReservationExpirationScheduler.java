package com.example.concertReservation.application.reservation;

import com.example.concertReservation.domain.common.event.SeatReleasedEvent;
import com.example.concertReservation.domain.reservation.Reservation;
import com.example.concertReservation.domain.reservation.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.concertReservation.domain.reservation.ReservationStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationExpirationScheduler {
    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void processExpiredReservations() {
        log.info("만료된 예약 처리 스케줄러 실행: {}", LocalDateTime.now());
        List<Reservation> expiredReservations = reservationRepository.findByStatusAndCreatedDateBefore(PENDING, LocalDateTime.now().minusMinutes(10));

        if (expiredReservations.isEmpty()) {
            log.info("만료된 예약이 없습니다.");
            return;
        }

        expiredReservations.forEach(
                reservation -> {
                    reservation.expire();
                    reservation.getSeats().forEach(reservationSeat ->
                            eventPublisher.publishEvent(new SeatReleasedEvent(reservationSeat.getSeatId()))
                    );
                }
        );
    }
}
