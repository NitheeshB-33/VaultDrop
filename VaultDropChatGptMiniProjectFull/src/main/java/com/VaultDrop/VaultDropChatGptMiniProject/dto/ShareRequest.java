package com.VaultDrop.VaultDropChatGptMiniProject.dto;

import java.time.LocalDateTime;

public class ShareRequest {

    private String username;
    private LocalDateTime expiresAt;

    public ShareRequest(String username,LocalDateTime expiresAt) {
        this.username = username;
        this.expiresAt=expiresAt;
    }

    public ShareRequest() {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
