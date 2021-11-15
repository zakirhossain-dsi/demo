package com.example.demo.service;

import com.example.demo.entity.StudentEntity;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentDAO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentDAO studentDAO;
    private final ModelMapper modelMapper;

    @Override
    public Student getStudentById(long studentId) {

        Optional<StudentEntity> possibleStudentEntity = studentDAO.findByStudentId(studentId);
        if(possibleStudentEntity.isEmpty()){
            throw new EntityNotFoundException(String.format("Student was not found for parameters {id=%s}", studentId));
        }
        return modelMapper.map(possibleStudentEntity.get(), Student.class);
    }

    @Override
    public Student createStudent(Student student){
        StudentEntity studentEntity = modelMapper.map(student, StudentEntity.class);
        studentEntity = studentDAO.save(studentEntity);
        student.setStudentId(studentEntity.getStudentId());
        return student;
    }
}
