package com.example.srs.Repository;

import com.example.srs.Model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepo extends JpaRepository<CourseEntity, Long> {
    CourseEntity findByCourseName(String courseName);

}
