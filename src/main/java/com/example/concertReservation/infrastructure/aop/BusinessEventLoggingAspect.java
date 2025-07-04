package com.example.concertReservation.infrastructure.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class BusinessEventLoggingAspect {
    private final ObjectMapper objectMapper = new ObjectMapper();

    // @TransactionalEventListener가 붙은 메서드를 대상으로 하는 포인트컷
    @Pointcut("@annotation(org.springframework.transaction.event.TransactionalEventListener)")
    public void transactionalEventListenerMethods() {}

    // 이벤트 리스너 실행 전후로 로깅을 추가하는 어드바이스
    @Around("transactionalEventListenerMethods() && @annotation(eventListener)")
    public Object logAroundTransactionalEventListener(ProceedingJoinPoint joinPoint,
                                                      TransactionalEventListener eventListener) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 이벤트 객체 (첫 번째 파라미터) 가져오기
        Object eventObject = joinPoint.getArgs()[0];
        String eventType = eventObject.getClass().getSimpleName();

        // 트랜잭션 페이즈 정보 가져오기
        TransactionPhase phase = eventListener.phase();

        // 이벤트 처리 시작 로깅
        log.info("==> [Business Event] 시작: {}.{}, 이벤트 타입: {}, 트랜잭션 페이즈: {}",
                className, methodName, eventType, phase);

        try {
            // 실제 이벤트 리스너 메서드 실행
            Object result = joinPoint.proceed();

            // 이벤트 처리 성공 로깅 (JSON 형식)
            Map<String, Object> logData = new HashMap<>();
            logData.put("eventType", eventType);
            logData.put("timestamp", Instant.now().toString());
            logData.put("status", "SUCCESS");
            logData.put("listenerClass", className);
            logData.put("listenerMethod", methodName);
            logData.put("transactionPhase", phase.toString());

            // 이벤트 객체에서 필요한 정보 추출 (리플렉션 사용)
            Map<String, Object> eventData = extractEventData(eventObject);
            logData.put("eventData", eventData);

            log.info("<== [Business Event] 완료: {}, 데이터: {}",
                    eventType, objectMapper.writeValueAsString(logData));

            return result;
        } catch (Exception e) {
            // 이벤트 처리 실패 로깅
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("eventType", eventType);
            errorData.put("timestamp", Instant.now().toString());
            errorData.put("status", "FAILED");
            errorData.put("errorMessage", e.getMessage());
            errorData.put("listenerClass", className);
            errorData.put("listenerMethod", methodName);

            log.error("!!! [Business Event] 실패: {}, 에러: {}",
                    eventType, objectMapper.writeValueAsString(errorData), e);

            throw e; // 예외 다시 던지기
        }
    }

    // 리플렉션을 사용해 이벤트 객체에서 데이터 추출
    private Map<String, Object> extractEventData(Object eventObject) {
        Map<String, Object> eventData = new HashMap<>();

        try {
            // 이벤트 객체의 모든 getter 메서드 찾기
            for (Method method : eventObject.getClass().getMethods()) {
                if (isGetter(method)) {
                    String fieldName = getFieldNameFromGetter(method);
                    Object value = method.invoke(eventObject);

                    // 민감 정보 필터링 (예: 비밀번호, 카드번호 등)
                    if (isSensitiveField(fieldName)) {
                        value = "******";
                    }

                    eventData.put(fieldName, value);
                }
            }
        } catch (Exception e) {
            log.warn("이벤트 데이터 추출 중 오류 발생: {}", e.getMessage());
            eventData.put("extractionError", "이벤트 데이터 추출 실패");
        }

        return eventData;
    }

    // getter 메서드인지 확인
    private boolean isGetter(Method method) {
        return method.getName().startsWith("get") &&
                method.getName().length() > 3 &&
                method.getParameterCount() == 0 &&
                !method.getReturnType().equals(void.class) &&
                !method.getName().equals("getClass");
    }

    // getter 메서드명에서 필드명 추출 (예: getName -> name)
    private String getFieldNameFromGetter(Method method) {
        String name = method.getName().substring(3); // "get" 제거
        return name.substring(0, 1).toLowerCase() + name.substring(1);
    }

    // 민감 정보 필드인지 확인
    private boolean isSensitiveField(String fieldName) {
        List<String> sensitiveFields = Arrays.asList(
                "password", "cardNumber", "ssn", "securityCode", "token"
        );

        return sensitiveFields.stream()
                .anyMatch(field -> fieldName.toLowerCase().contains(field.toLowerCase()));
    }
}