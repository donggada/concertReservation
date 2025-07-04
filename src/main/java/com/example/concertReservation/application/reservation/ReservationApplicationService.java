package com.example.concertReservation.application.reservation;

import com.example.concertReservation.api.concert.ConcertServiceApi;
import com.example.concertReservation.api.seat.SeatServiceApi;
import com.example.concertReservation.application.reservation.dto.CreateReservationRequest;
import com.example.concertReservation.application.reservation.dto.ReservationResponse;
import com.example.concertReservation.application.reservation.dto.ReservationSeatRequest;
import com.example.concertReservation.domain.common.event.CancelReservationEvent;
import com.example.concertReservation.domain.concert.Concert;
import com.example.concertReservation.domain.reservation.Reservation;
import com.example.concertReservation.domain.reservation.ReservationRepository;
import com.example.concertReservation.domain.reservation.ReservationSeat;
import com.example.concertReservation.domain.seat.SeatLockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.SEAT_LOCK_NOT_HELD;

@Service
@RequiredArgsConstructor
public class ReservationApplicationService {
    private final ReservationRepository reservationRepository;
    private final SeatLockService seatLockService;
    private final SeatServiceApi seatServiceApi;
    private final ConcertServiceApi concertServiceApi;
    private final ApplicationEventPublisher eventPublisher;



    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest request, Long memberId) {
        List<ReservationSeatRequest> reservationSeatRequestList = request.seatRequestList();

        reservationSeatRequestList.forEach(reservationSeatRequest -> {
                    if(!seatLockService.isLockedBy(reservationSeatRequest.seatId(), memberId)){
                        throw SEAT_LOCK_NOT_HELD.build(reservationSeatRequest.seatId(), memberId);
                    }
                }
        );

        validateCreate(request);

        Reservation reservation = Reservation.createReservation(request, memberId);
        List<ReservationSeat> reservationSeatList = reservationSeatRequestList.stream()
                .map(reservationSeatRequest -> ReservationSeat.createReservationSeat(reservationSeatRequest, reservation))
                .toList();
        reservation.addReservationSeat(reservationSeatList);

        reservationRepository.save(reservation);

        Concert concert = concertServiceApi.findConcert(request.concertId());
        return new ReservationResponse(concert.getTitle(), reservation.getTotalAmount());
    }

    @Transactional
    public void cancelReservation(Long reservationId) {
        eventPublisher.publishEvent(new CancelReservationEvent(reservationId));
    }

    private void validateCreate(CreateReservationRequest request) {
        concertServiceApi.findConcert(request.concertId());
        request.seatRequestList().forEach(
                reservationSeatRequest -> seatServiceApi.findSeat(reservationSeatRequest.seatId())
        );
    }
}
