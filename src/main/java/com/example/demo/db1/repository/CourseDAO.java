package com.example.demo.db1.repository;

import com.example.demo.entity.CourseEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDAO extends CrudRepository<CourseEntity, Long> {

  Optional<CourseEntity> findById(long id);
}
