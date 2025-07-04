package com.example.concertReservation.api.concert;

import com.example.concertReservation.domain.concert.Concert;

public interface ConcertServiceApi {
    Concert findConcert(Long concertId);
}
