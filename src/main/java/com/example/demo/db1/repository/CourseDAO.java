package com.example.demo.db1.repository;

import com.example.demo.db1.entity.CourseEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDAO extends JpaRepository<CourseEntity, Long> {

  Optional<CourseEntity> findById(long id);
}
