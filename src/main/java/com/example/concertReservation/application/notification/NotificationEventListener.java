package com.example.concertReservation.application.notification;

import com.example.concertReservation.domain.common.event.NoticePaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {
    private final NoticePortFactory noticePortFactory;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationCompleted(NoticePaymentCompletedEvent event) {
        try {
            noticePortFactory.getService(NotificationType.SMS).send("", "");
            noticePortFactory.getService(NotificationType.KAKAO_TALK).send("", "");
            noticePortFactory.getService(NotificationType.EMAIL).send("", "");
        } catch (Exception e) {
            log.error("알림 발송 실패: 예약 ID " + event.reservationId() + ", 에러: " + e.getMessage());
        }
    }
}
