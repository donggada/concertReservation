package com.example.concertReservation.infrastructure.external.seat;


import com.example.concertReservation.api.seat.SeatServiceApi;
import com.example.concertReservation.domain.seat.Seat;
import com.example.concertReservation.domain.seat.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_SEAT;


@Service
@RequiredArgsConstructor
public class SeatServiceApiJpa implements SeatServiceApi {
    private final SeatRepository seatRepository;

    @Override
    public Seat findSeat(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(() -> INVALID_SEAT.build(seatId));
    }
}
