package com.example.concertReservation.api.seat;

import com.example.concertReservation.domain.seat.Seat;
import org.springframework.stereotype.Service;

@Service
public interface SeatServiceApi {

    Seat findSeat(Long seatId);
}
