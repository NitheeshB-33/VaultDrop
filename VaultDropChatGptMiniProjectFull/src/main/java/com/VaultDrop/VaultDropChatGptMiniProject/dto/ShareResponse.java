package com.VaultDrop.VaultDropChatGptMiniProject.dto;

import java.time.LocalDateTime;

public class ShareResponse {

    private Long shareId;
    private Long fileId;
    private String fileName;
    private String username;
    private LocalDateTime expiresAt;
    private Integer userId;
    public ShareResponse() {
    }

    public ShareResponse(Long shareId, Long fileId, String fileName,
                         String username, LocalDateTime expiresAt, Integer userId) {
        this.shareId = shareId;
        this.fileId = fileId;
        this.fileName = fileName;
        this.username = username;
        this.expiresAt = expiresAt;
        this.userId= userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Long getShareId() {
        return shareId;
    }

    public Long getFileId() {
        return fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }
}