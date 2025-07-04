package com.example.concertReservation.infrastructure.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class RequestResponseLoggingFilter extends OncePerRequestFilter {
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 요청과 응답을 캐싱할 수 있는 래퍼로 감싸기
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            // 요청 URI와 바디만 로깅
            logSimpleRequest(requestWrapper);

            // 다음 필터 실행 (실제 컨트롤러 호출)
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            // 응답 시간과 바디만 로깅
            logSimpleResponse(responseWrapper, request.getRequestURI(), System.currentTimeMillis() - startTime);

            // 중요: 응답 본문을 복원해야 함 (안 하면 클라이언트가 빈 응답을 받음)
            responseWrapper.copyBodyToResponse();
        }
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        // 특정 경로는 로깅하지 않을 수 있음 (예: 정적 리소스, 헬스 체크 등)
        String path = request.getRequestURI();
        return path.contains("/actuator") || path.contains("/static");
    }

    private void logSimpleRequest(ContentCachingRequestWrapper request) {
        // URI와 요청 본문만 로깅
        String uri = request.getRequestURI();
        byte[] content = request.getContentAsByteArray();
        String body = content.length > 0 ? new String(content, StandardCharsets.UTF_8) : "빈 요청 바디";

        log.info("Request - URI: {}, Body: {}", uri, body);
    }

    private void logSimpleResponse(ContentCachingResponseWrapper response, String uri, long timeElapsed) {
        // 응답 본문과 처리 시간만 로깅
        byte[] content = response.getContentAsByteArray();
        String body = content.length > 0 ? new String(content, StandardCharsets.UTF_8) : "빈 응답 바디";

        log.info("Response - URI: {}, Time: {}ms, Body: {}", uri, timeElapsed, body);
    }
}

