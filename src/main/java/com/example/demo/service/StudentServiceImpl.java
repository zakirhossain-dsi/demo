package com.example.demo.service;

import com.example.demo.config.StorageProperties;
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
        Student student =  modelMapper.map(possibleStudentEntity.get(), Student.class);
        student.setProfileImagePath(buildProfileImagePath(student));
        return student;
    }

    @Override
    public Student saveStudent(Student student){
        StudentEntity studentEntity = modelMapper.map(student, StudentEntity.class);

        studentEntity = studentDAO.save(studentEntity);

        student.setStudentId(studentEntity.getStudentId());
        student.setProfileImagePath(buildProfileImagePath(student));
        storageService.store(student.getProfileImagePath(), student.getProfileImage());

        student.setProfileImage(null);
        return student;
    }

    private String buildProfileImagePath(Student student){
        return String.format("%s/%s-%s.jpeg",
                storageProperties.getProfileImageDir(),
                student.getStudentId(),
                student.getFirstName());

    }
}
