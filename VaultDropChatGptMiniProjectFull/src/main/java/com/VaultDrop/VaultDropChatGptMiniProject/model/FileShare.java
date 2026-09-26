package com.VaultDrop.VaultDropChatGptMiniProject.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "file_shares")
public class FileShare {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long fileId;
    private Integer ownerId;
    private Integer sharedWithUserId;
    private LocalDateTime expiresAt;


    public FileShare() {
    }


    public FileShare(long id, long fileId, Integer ownerId, Integer sharedWithUserId, LocalDateTime expiresAt) {
        this.id = id;
        this.fileId = fileId;
        this.ownerId = ownerId;
        this.sharedWithUserId = sharedWithUserId;
        this.expiresAt=expiresAt;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getSharedWithUserId() {
        return sharedWithUserId;
    }

    public void setSharedWithUserId(Integer sharedWithUserId) {
        this.sharedWithUserId = sharedWithUserId;
    }


    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
