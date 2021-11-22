package com.example.demo.repository;

import com.example.demo.entity.StudentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentDAO extends CrudRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByEmail(String email);
    Optional<StudentEntity> findById(long Id);
}
