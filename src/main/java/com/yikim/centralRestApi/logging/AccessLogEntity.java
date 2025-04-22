package com.yikim.centralRestApi.logging;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 */
@Table("SY_ACCESS_LOG_HT")
public class AccessLogEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동으로 ID가 생성되도록 설정
    private String id;
    @Column("USER_ID")
    private String userId;
    @Column("IP_ADDRESS")
    private String ipAddress;
    @Column("ACCESS_INFO")
    private String accessInfo;
    @Column("RESULT")
    private String result;

    public String getId() { return id; }
    public void setId(String id) { this.id = id;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) {this.userId = userId;}

    public String getIpAddress() {return ipAddress;}
    public void setIpAddress(String ipAddress) {this.ipAddress = ipAddress;}

    public String getAccessInfo() {return accessInfo;}
    public void setAccessInfo(String accessInfo) {this.accessInfo = accessInfo;}

    public String getResult() {return result;}
    public void setResult(String result) {this.result = result;}
}
