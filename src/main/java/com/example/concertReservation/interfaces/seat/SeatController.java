package com.example.concertReservation.interfaces.seat;

import com.example.concertReservation.application.seat.SeatApplicationService;
import com.example.concertReservation.application.seat.dto.SeatSelectionRequest;
import com.example.concertReservation.application.seat.dto.SeatSelectionResponse;
import com.example.concertReservation.infrastructure.security.AuthMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatApplicationService seatApplicationService;

    @PostMapping("/select")
    public SeatSelectionResponse selectSeats(
            @AuthMemberId Long memberId,
            @RequestBody SeatSelectionRequest request
    ) {
        SeatSelectionResponse response = seatApplicationService.selectedSeat(request, memberId);
        return response;
    }

}
