package com.example.concertReservation.infrastructure.external.notification;

import com.example.concertReservation.application.notification.NoticePort;
import org.springframework.stereotype.Service;

@Service
public class KakaoTalkNoticeAdapter implements NoticePort {
    @Override
    public void send(String title, String content) {

    }
}
