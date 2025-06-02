package com.SRS.SRS.Repository;

import com.SRS.SRS.Models.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByEmail(String email);

    Optional<AdminEntity> findByUsername(String username);
}
