package com.example.concertReservation.infrastructure.external.reservation;


import com.example.concertReservation.api.reservation.ReservationServiceApi;
import com.example.concertReservation.api.seat.SeatServiceApi;
import com.example.concertReservation.domain.reservation.Reservation;
import com.example.concertReservation.domain.reservation.ReservationRepository;
import com.example.concertReservation.domain.seat.Seat;
import com.example.concertReservation.domain.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_RESERVATION;


@Service
@RequiredArgsConstructor
public class ReservationServiceApiJpa implements ReservationServiceApi {
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> INVALID_RESERVATION.build(reservationId));
    }
}
