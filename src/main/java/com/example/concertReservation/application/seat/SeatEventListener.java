package com.example.concertReservation.application.seat;

import com.example.concertReservation.domain.common.event.IncrementAvailableSeatsEvent;
import com.example.concertReservation.domain.common.event.ReservedSeatsEvent;
import com.example.concertReservation.domain.common.event.SeatReleasedEvent;
import com.example.concertReservation.domain.seat.Seat;
import com.example.concertReservation.domain.seat.SeatRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_SEAT;

@Service
@RequiredArgsConstructor
public class SeatEventListener {
    private final SeatRepository seatRepository;
    private final ApplicationEventPublisher eventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void increaseConcertAvailableSeats(SeatReleasedEvent event) {
        Seat seat = seatRepository.findById(event.seatId()).orElseThrow(() -> INVALID_SEAT.build(event.seatId()));
        seat.release();
        eventPublisher.publishEvent(new IncrementAvailableSeatsEvent(seat.getConcertId()));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void reservedSeats(ReservedSeatsEvent event) {
        Seat seat = seatRepository.findById(event.seatId()).orElseThrow(() -> INVALID_SEAT.build(event.seatId()));
        seat.reserved();
    }
}
