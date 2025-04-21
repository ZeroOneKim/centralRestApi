package com.yikim.centralRestApi.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

@Service
public class AccessLogService {
    @Autowired AccessLogRepository accessLogRepository;

    public void accessLog(String userId, String ipAddress, String accessInfo, String res) {
        System.out.println("TEST");

        AccessLogEntity accessLogEntity = new AccessLogEntity();

        accessLogEntity.setUserId(userId);
        accessLogEntity.setIpAddress(ipAddress);
        accessLogEntity.setAccessInfo(accessInfo);
        accessLogEntity.setResult(res);

        accessLogRepository.save(accessLogEntity).subscribe();

    }

    public String getClientIp(ServerHttpRequest request) {
        String ip = request.getHeaders().getFirst("X-Forwarded-For");

        if(ip == null) ip = request.getRemoteAddress().getAddress().getHostAddress();
        if(ip == null) ip = "unknown";

        System.out.println(ip);

        return ip;
    }
}
