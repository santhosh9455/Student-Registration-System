package com.SRS.SRS.Repository;

import com.SRS.SRS.Models.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<StudentEntity, Long> {

    // Find the Details by mail id
    Optional<StudentEntity> findByEmail(String email);

    //Find the Details by username
    Optional<StudentEntity> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

}
