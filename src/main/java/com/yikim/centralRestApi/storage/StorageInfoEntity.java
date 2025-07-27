package com.yikim.centralRestApi.storage;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("STORAGE_FILE_INFO")
public class StorageInfoEntity {
    @Column("USER_ID")
    private String userId;

    @Column("FILE_NAME")
    private String fileName;

    @Column("FILE_SIZE_BYTE")
    private Long FileSize;

    @Column("RGST_DT")
    private LocalDateTime rgstDt;

    public StorageInfoEntity() {
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return FileSize;
    }
    public void setFileSize(Long fileSize) {
        FileSize = fileSize;
    }

    public LocalDateTime getRgstDt() {
        return rgstDt;
    }
    public void setRgstDt(LocalDateTime rgstDt) {
        this.rgstDt = rgstDt;
    }
}
