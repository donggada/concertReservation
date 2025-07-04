package com.example.concertReservation.infrastructure.external.notification;

import com.example.concertReservation.application.notification.NoticePort;
import org.springframework.stereotype.Service;

@Service
public class EmailNoticeAdapter implements NoticePort {
    @Override
    public void send(String title, String content) {

    }
}
