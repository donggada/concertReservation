package com.example.concertReservation.infrastructure.external.concert;


import com.example.concertReservation.api.concert.ConcertServiceApi;
import com.example.concertReservation.domain.concert.Concert;
import com.example.concertReservation.domain.concert.ConcertRepository;
import com.example.concertReservation.infrastructure.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ConcertServiceApiJpa implements ConcertServiceApi {
    private final ConcertRepository concertRepository;

    @Override
    public Concert findConcert(Long concertId) {
        return concertRepository.findById(concertId).orElseThrow(() -> ErrorCode.INVALID_CONCERT.build(concertId));
    }
}
