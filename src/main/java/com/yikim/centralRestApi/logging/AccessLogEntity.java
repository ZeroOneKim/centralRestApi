package com.yikim.centralRestApi.logging;

/**
 *
 */
public class AccessLogEntity {
    private long id;
    private String userId;
    private String ipAddress;
    private String accessInfo;
    private String result;

    public long getId() { return id; }
    public void setId(long id) { this.id = id;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public String getIpAddress() {return ipAddress;}
    public void setIpAddress(String ipAddress) {this.ipAddress = ipAddress;}

    public String getAccessInfo() {return accessInfo;}
    public void setAccessInfo(String accessInfo) {this.accessInfo = accessInfo;}

    public String getResult() {return result;}
    public void setResult(String result) {this.result = result;}
}
