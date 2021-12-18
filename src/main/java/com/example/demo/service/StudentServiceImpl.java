package com.example.demo.service;

import com.example.demo.config.StorageProperties;
import com.example.demo.entity.StudentEntity;
import com.example.demo.model.ModelType;
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
    private final RedisService cacheService;

    @Override
    public Student getStudentById(Long id) {
        Student student = (Student) cacheService.getModel(ModelType.STUDENT, id);
        if(!Objects.isNull(student)){
            return student;
        }

        Optional<StudentEntity> possibleStudentEntity = studentDAO.findById(id);
        if(possibleStudentEntity.isEmpty()){
            throw new EntityNotFoundException(String.format("Student was not found for parameters {id=%s}", id));
        }

        student = modelMapper.map(possibleStudentEntity.get(), Student.class);

         cacheService.saveModel(student);
        return student;
    }

    @Override
    public Student insertStudent(Student student){

        StudentEntity studentEntity = modelMapper.map(student, StudentEntity.class);
        studentEntity = studentDAO.save(studentEntity);

        if(!Objects.isNull(student.getCourseRatings())){
            studentEntity.setCourseRatings(student.getCourseRatingEntitySet(studentEntity));
            studentEntity = studentDAO.save(studentEntity);
        }

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

    @Override
    public Student updateStudent(Student student){

        Optional<StudentEntity> possibleStudentEntity = studentDAO.findById(student.getStudentId());

        StudentEntity studentEntity = possibleStudentEntity.orElseThrow(()->
            new EntityNotFoundException(String.format("Student was not found for parameters {id=%s}", student.getStudentId()))
        );

        studentEntity.setCourseRatings(student.getCourseRatingEntitySet(studentEntity));

        studentEntity = studentDAO.save(studentEntity);

        if(!Objects.isNull(student.getProfileImage())) {
            student.setProfileImagePath(buildProfileImagePath(student));
            studentEntity.setProfileImagePath(student.getProfileImagePath());
            storageService.store(student.getProfileImagePath(), student.getProfileImage());
            studentDAO.save(studentEntity);
            student.setProfileImage(null);
        }

        cacheService.deleteKey(student.getModelType(), student.getStudentId());

        return student;
    }

    private String buildProfileImagePath(Student student){
        return String.format("%s/%s-%s.jpeg",
                storageProperties.getProfileImageDir(),
                student.getStudentId(),
                student.getFirstName());

    }
}
