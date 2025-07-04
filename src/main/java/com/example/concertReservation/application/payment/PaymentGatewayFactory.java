package com.example.concertReservation.application.payment;

import com.example.concertReservation.domain.payment.PaymentGatewayPort;
import com.example.concertReservation.domain.payment.PgType;
import com.example.concertReservation.infrastructure.external.payment.InicisPaymentsAdapter;
import com.example.concertReservation.infrastructure.external.payment.KakaoPaymentsAdapter;
import com.example.concertReservation.infrastructure.external.payment.NaverPaymentsAdapter;
import com.example.concertReservation.infrastructure.external.payment.TossPaymentsAdapter;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.concertReservation.infrastructure.exception.ErrorCode.INVALID_SERVICE;

@Component
public class PaymentGatewayFactory {

    private final Map<Class, PaymentGatewayPort> serviceMap;

    public PaymentGatewayFactory(List<PaymentGatewayPort> serviceList) {
        this.serviceMap = serviceList.stream().collect(
                Collectors.toMap(
                        AopUtils::getTargetClass,
                        service ->service
                )
        );
    }

    public PaymentGatewayPort getService(PgType type) {
        return switch (type) {
            case TOSS -> serviceMap.get(TossPaymentsAdapter.class);
            case KAKAO_PAY -> serviceMap.get(KakaoPaymentsAdapter.class);
            case NAVER_PAY -> serviceMap.get(NaverPaymentsAdapter.class);
            case INICIS -> serviceMap.get(InicisPaymentsAdapter.class);
            default -> throw INVALID_SERVICE.build(type);
        };
    }
}
