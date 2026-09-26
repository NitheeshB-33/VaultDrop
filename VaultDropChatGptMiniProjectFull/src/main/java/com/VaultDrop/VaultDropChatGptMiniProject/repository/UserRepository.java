package com.VaultDrop.VaultDropChatGptMiniProject.repository;

import com.VaultDrop.VaultDropChatGptMiniProject.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String username);
}
