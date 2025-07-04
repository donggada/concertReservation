package com.example.concertReservation.application.reservation;

import com.example.concertReservation.domain.common.event.*;
import com.example.concertReservation.domain.reservation.Reservation;
import com.example.concertReservation.domain.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_RESERVATION;

@Service
@RequiredArgsConstructor
public class ReservationEventListener {
    private final ApplicationEventPublisher eventPublisher;
    private final ReservationRepository reservationRepository;


    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void completedReservation(CompletedReservationEvent event) {
        Reservation reservation = reservationRepository.findById(event.reservationId()).orElseThrow(() -> INVALID_RESERVATION.build(event.reservationId()));
        reservation.completed();
        reservation.getSeats().forEach(
                reservationSeat -> eventPublisher.publishEvent(new ReservedSeatsEvent(reservationSeat.getSeatId()))
        );
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void completedPayment(CompletedPaymentEvent event) {
        Reservation reservation = reservationRepository.findById(event.reservationId()).orElseThrow(() -> INVALID_RESERVATION.build(event.reservationId()));
        reservation.completed();
        reservation.updatePaymentId(event.paymentId());
        eventPublisher.publishEvent(new NoticePaymentCompletedEvent(event.reservationId()));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void cancelReservation(CancelReservationEvent event) {
        Reservation reservation = reservationRepository.findById(event.reservationId()).orElseThrow(() -> INVALID_RESERVATION.build(event.reservationId()));
        reservation.cancel();
        reservation.getSeats().forEach(
                reservationSeat -> eventPublisher.publishEvent(new SeatReleasedEvent(reservationSeat.getSeatId()))
        );
    }
}
