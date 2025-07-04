package com.example.concertReservation.interfaces.reservation;


import com.example.concertReservation.application.reservation.ReservationApplicationService;
import com.example.concertReservation.application.reservation.dto.CreateReservationRequest;
import com.example.concertReservation.application.reservation.dto.ReservationResponse;
import com.example.concertReservation.infrastructure.security.AuthMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationApplicationService reservationApplicationService;

    @PostMapping
    public ReservationResponse createReservation(
            @AuthMemberId Long memberId,
            @RequestBody CreateReservationRequest request
    ) {
        return reservationApplicationService.createReservation(request, memberId);
    }

    @DeleteMapping("/{reservationId}")
    public void cancelReservation(
            @PathVariable Long reservationId
    ) {
        reservationApplicationService.cancelReservation(reservationId);
    }
}