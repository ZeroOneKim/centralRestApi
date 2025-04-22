package com.yikim.centralRestApi.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

/**
 * 로깅 처리 서비스. 특정 조건 하에 유저의 접속 IP, 요청 정보, 결과 등을 로그 테이블에 저장.
 *
 * @Version : 0.95
 * @Since  : 2025-04-25
 * @Author : 김영일
 */
@Service
public class AccessLogService {
    private boolean loggingService = false; //로깅 활성화 여부 제어

    @Autowired AccessLogRepository accessLogRepository;

    /**
     * 유저의 접근 로그를 저장
     *
     * @param userId     사용자 ID
     * @param ipAddress  사용자 IP 주소
     * @param accessInfo 접속 시도에 대한 설명 또는 메타정보
     * @param res        결과 값 (성공/실패 등)
     */
    public void accessLog(String userId, String ipAddress, String accessInfo, String res) {
        if(loggingService) {
            AccessLogEntity accessLogEntity = new AccessLogEntity();

            accessLogEntity.setUserId(userId);
            accessLogEntity.setIpAddress(ipAddress);
            accessLogEntity.setAccessInfo(accessInfo);
            accessLogEntity.setResult(res);

            accessLogRepository.save(accessLogEntity).subscribe();
        }
    }

    /**
     * 클라이언트의 실제 IP 주소를 추출.
     * 기본적으로 "X-Forwarded-For" 헤더를 우선 사용하며, 해당 값이 없는 경우 기본 remote address 를 사용함
     *
     * @param request ServerHttpRequest
     * @return 클라이언트 IP 주소 (없을 경우 "unknown" 반환)
     */
    public String getClientIp(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("X-Forwarded-For");

        if(ip == null) ip = request.getRemoteAddress().getAddress().getHostAddress();
        if(ip == null) ip = "unknown";

        return ip;
    }
}
