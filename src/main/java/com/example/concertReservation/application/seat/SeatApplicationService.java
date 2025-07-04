package com.example.concertReservation.application.seat;

import com.example.concertReservation.application.seat.dto.SeatSelectionRequest;
import com.example.concertReservation.application.seat.dto.SeatSelectionResponse;
import com.example.concertReservation.domain.common.event.DecreaseAvailableSeatsEvent;
import com.example.concertReservation.domain.seat.Seat;
import com.example.concertReservation.domain.seat.SeatLockService;
import com.example.concertReservation.domain.seat.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_SEAT;
import static com.example.concertReservation.infrastructure.exception.ErrorCode.SEAT_ALREADY_SELECTED;

@Service
@RequiredArgsConstructor
public class SeatApplicationService {
    private final SeatRepository seatRepository;
    private final SeatLockService seatLockService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public SeatSelectionResponse selectedSeat(SeatSelectionRequest request, Long memberId) {
        List<Seat> seatList = seatRepository.findAllById(request.seatIdList());

        if (seatList.size() != request.seatIdList().size()) {
            throw INVALID_SEAT.build(0L);
        }

        seatList.forEach(seat -> {
                    boolean locked = seatLockService.lockSeat(seat.getId(), memberId);

                    if (!locked) {
                        throw SEAT_ALREADY_SELECTED.build(seat.getId(), memberId);
                    }


                    seat.select();
                    eventPublisher.publishEvent(new DecreaseAvailableSeatsEvent(seat.getConcertId()));
                }
        );

        return null;
    }
}
