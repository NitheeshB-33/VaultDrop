package com.VaultDrop.VaultDropChatGptMiniProject.repository;

import com.VaultDrop.VaultDropChatGptMiniProject.model.FileShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileShareRepository extends JpaRepository<FileShare, Long> {

    List<FileShare> findBySharedWithUserId(Integer userId);

    Optional<FileShare> findByFileIdAndSharedWithUserId(Long fileId, Integer userId);

    List<FileShare> findByOwnerId(Integer ownerId);

}
