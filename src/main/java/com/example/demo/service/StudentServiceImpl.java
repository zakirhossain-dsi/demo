package com.example.demo.service;

import com.example.demo.config.StorageProperties;
import com.example.demo.entity.StudentEntity;
import com.example.demo.model.Student;
import com.example.demo.repository.StudentDAO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;
    private final ModelMapper modelMapper;
    private final StorageService storageService;
    private final StorageProperties storageProperties;

    @Override
    public Student getStudentById(long studentId) {

        Optional<StudentEntity> possibleStudentEntity = studentDAO.findByStudentId(studentId);
        if(possibleStudentEntity.isEmpty()){
            throw new EntityNotFoundException(String.format("Student was not found for parameters {id=%s}", studentId));
        }
        return modelMapper.map(possibleStudentEntity.get(), Student.class);
    }

    @Override
    public Student saveStudent(Student student){
        StudentEntity studentEntity = modelMapper.map(student, StudentEntity.class);

        studentEntity = studentDAO.save(studentEntity);
        student.setStudentId(studentEntity.getStudentId());

        if(!Objects.isNull(student.getProfileImage())) {
            student.setProfileImagePath(buildProfileImagePath(student));
            studentEntity.setProfileImagePath(student.getProfileImagePath());
            storageService.store(student.getProfileImagePath(), student.getProfileImage());
            studentDAO.save(studentEntity);
            student.setProfileImage(null);
        }

        return student;
    }

    private String buildProfileImagePath(Student student){
        return String.format("%s/%s-%s.jpeg",
                storageProperties.getProfileImageDir(),
                student.getStudentId(),
                student.getFirstName());

    }
}
