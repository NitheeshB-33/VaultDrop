package com.VaultDrop.VaultDropChatGptMiniProject.repository;


import com.VaultDrop.VaultDropChatGptMiniProject.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByOwnerId(Integer ownerId);

}
