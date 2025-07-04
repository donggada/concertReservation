package com.example.concertReservation.application.concert;


import com.example.concertReservation.domain.common.event.DecreaseAvailableSeatsEvent;
import com.example.concertReservation.domain.common.event.IncrementAvailableSeatsEvent;
import com.example.concertReservation.domain.concert.ConcertRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_CONCERT;

@Service
@RequiredArgsConstructor
public class ConcertEventListener {
    private final ConcertRepository concertRepository;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void decreaseConcertAvailableSeats(DecreaseAvailableSeatsEvent event) {
        concertRepository.findById(event.concertId())
                .orElseThrow(() -> INVALID_CONCERT.build(event.concertId()))
                .decrementAvailableSeats();
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void incrementAvailableSeats(IncrementAvailableSeatsEvent event) {
        concertRepository.findById(event.concertId())
                .orElseThrow(() -> INVALID_CONCERT.build(event.concertId()))
                .incrementAvailableSeats();
    }
}
