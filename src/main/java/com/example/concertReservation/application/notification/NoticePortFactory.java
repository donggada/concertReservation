package com.example.concertReservation.application.notification;

import com.example.concertReservation.infrastructure.external.notification.EmailNoticeAdapter;
import com.example.concertReservation.infrastructure.external.notification.KakaoTalkNoticeAdapter;
import com.example.concertReservation.infrastructure.external.notification.SMSNoticeAdapter;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_SERVICE;

@Component
public class NoticePortFactory {

    private final Map<Class, NoticePort> serviceMap;

    public NoticePortFactory(List<NoticePort> serviceList) {
        this.serviceMap = serviceList.stream().collect(
                Collectors.toMap(
                        AopUtils::getTargetClass,
                        service ->service
                )
        );
    }

    public NoticePort getService(NotificationType type) {
        return switch (type) {
            case EMAIL -> serviceMap.get(EmailNoticeAdapter.class);
            case SMS -> serviceMap.get(SMSNoticeAdapter.class);
            case KAKAO_TALK -> serviceMap.get(KakaoTalkNoticeAdapter.class);
            default -> throw INVALID_SERVICE.build(type);
        };
    }
}
