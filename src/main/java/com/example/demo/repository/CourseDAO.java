package com.example.demo.repository;

import com.example.demo.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseDAO extends CrudRepository<CourseEntity, Long> {

    Optional<CourseEntity> findById(long id);
}
