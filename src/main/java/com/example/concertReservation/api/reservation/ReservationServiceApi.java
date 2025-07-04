package com.example.concertReservation.api.reservation;

import com.example.concertReservation.domain.reservation.Reservation;
import org.springframework.stereotype.Service;

@Service
public interface ReservationServiceApi {

    Reservation findReservation(Long reservationId);
}
