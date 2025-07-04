package com.example.concertReservation.interfaces.waitingRoom;

import com.example.concertReservation.application.waitingroom.WaitingRoomApplicationService;
import com.example.concertReservation.application.waitingroom.dto.WaitingRoomEntryResponse;
import com.example.concertReservation.application.waitingroom.dto.WaitingRoomStatusResponse;
import com.example.concertReservation.infrastructure.security.AuthMemberId;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/waiting-room")
@RequiredArgsConstructor
public class WaitingRoomController {

    private final WaitingRoomApplicationService waitingRoomApplicationService;

    @PostMapping("/enter")
    public WaitingRoomEntryResponse enterWaitingRoom(
            @RequestParam Long concertId,
            @RequestParam String admissionToken,
            @AuthMemberId Long memberId
    ) {

        if (waitingRoomApplicationService.isAllowedByToken(concertId, memberId, admissionToken)) {
            //에약페이지로 이동
            return WaitingRoomEntryResponse.of(0);
        }

        return waitingRoomApplicationService.enterWaitingRoom(concertId, memberId);
    }

    @GetMapping("/rank")
    public WaitingRoomEntryResponse getRank(@RequestParam Long concertId,
                                                @AuthMemberId Long memberId) {
        return waitingRoomApplicationService.getRank(concertId, memberId);
    }




    @GetMapping("/touch")
    String touch(@RequestParam Long concertId,
                 @AuthMemberId Long memberId) {
        return waitingRoomApplicationService.generateToken(concertId, memberId);
    }
}