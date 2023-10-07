package com.example.demo.db1.service;

import com.example.demo.config.StorageProperties;
import com.example.demo.db1.entity.QStudentEntity;
import com.example.demo.db1.entity.StudentEntity;
import com.example.demo.db1.repository.StudentDAO;
import com.example.demo.model.ModelType;
import com.example.demo.model.Student;
import com.example.demo.model.StudentCourseRating;
import com.iwp.service.response.Execute;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.io.File;
import java.util.*;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

  private final StudentDAO studentDAO;
  private final ModelMapper modelMapper;
  private final StorageService storageService;
  private final StorageProperties storageProperties;
  private final RedisService cacheService;
  private final JPAQueryFactory queryFactory;

  @Qualifier("db2DataSource")
  private final JdbcTemplate jdbcTemplate;

  //  @PersistenceContext
  //  private EntityManager entityManager;

  @Override
  public Student getStudentById(Long id) {
    Student student = (Student) cacheService.getModel(ModelType.STUDENT, id);
    if (!Objects.isNull(student)) {
      return student;
    }

    Optional<StudentEntity> possibleStudentEntity = studentDAO.findById(id);
    if (possibleStudentEntity.isEmpty()) {
      throw new EntityNotFoundException(
          String.format("Student was not found for parameters {id=%s}", id));
    }

    student = modelMapper.map(possibleStudentEntity.get(), Student.class);

    cacheService.saveModel(student);
    return student;
  }

  @Override
  public Student[] getStudentsByQueryParam(Map<String, String> queryParams) {
    QStudentEntity student = QStudentEntity.studentEntity;
    NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

    JPAQuery<StudentEntity> query = queryFactory.selectFrom(student);

    if (!StringUtils.isEmpty(queryParams.get("lastName"))) {
      query.where(student.lastName.eq(queryParams.get("lastName").trim()));
    }

    query.orderBy(student.createDate.asc(), student.email.asc());

    List<StudentEntity> studentEntities = query.fetch();
    return modelMapper.map(studentEntities, Student[].class);

    /*
    List<StudentEntity> studentEntities = queryFactory.selectFrom(student)
            .where(student.lastName.eq("hossain"))
            .orderBy(student.createDate.asc(), student.email.asc())
            .fetch();
    return modelMapper.map(studentEntities, Student[].class);
    */

    /*
    List<Tuple> studentEntities =
        queryFactory
            .select(student.lastName, student.studentId.count().as(count))
            .from(student)
            .groupBy(student.lastName)
            .where(student.lastName.eq("hossain"))
            .orderBy(count.desc())
            .fetch();
    return modelMapper.map(studentEntities, Student[].class);
    */
  }

  @Override
  public Student insertStudent(Student student) {

    StudentEntity studentEntity = modelMapper.map(student, StudentEntity.class);
    studentEntity = studentDAO.save(studentEntity);

    if (!Objects.isNull(student.getCourseRatings())) {
      studentEntity.setCourseRatingList(student.getCourseRatingEntitySet(studentEntity));
      studentEntity = studentDAO.save(studentEntity);
    }

    student.setStudentId(studentEntity.getStudentId());

    if (!Objects.isNull(student.getProfileImage())) {
      student.setProfileImagePath(buildProfileImagePath(student));
      studentEntity.setProfileImagePath(student.getProfileImagePath());
      storageService.store(student.getProfileImagePath(), student.getProfileImage());
      studentDAO.save(studentEntity);
      student.setProfileImage(null);
    }

    return student;
  }

  @Override
  public Student updateStudent(Student student) {

    Optional<StudentEntity> possibleStudentEntity = studentDAO.findById(student.getStudentId());

    StudentEntity studentEntity =
        possibleStudentEntity.orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format(
                        "Student was not found for parameters {id=%s}", student.getStudentId())));

    studentEntity.setCourseRatingList(student.getCourseRatingEntitySet(studentEntity));

    studentEntity = studentDAO.save(studentEntity);

    if (!Objects.isNull(student.getProfileImage())) {
      student.setProfileImagePath(buildProfileImagePath(student));
      studentEntity.setProfileImagePath(student.getProfileImagePath());
      storageService.store(student.getProfileImagePath(), student.getProfileImage());
      studentDAO.save(studentEntity);
      student.setProfileImage(null);
    }

    cacheService.deleteKey(student.getModelType(), student.getStudentId());

    return student;
  }

  private String buildProfileImagePath(Student student) {
    return String.format(
        "%s/%s-%s.jpeg",
        storageProperties.getProfileImageDir(), student.getStudentId(), student.getFirstName());
  }

  @Override
  public List<StudentCourseRating> getStudentCourseRatingsPerStudentId(Long studentId) {
    return studentDAO.findStudentCourseRatingsPerStudentId1(studentId);
  }

  @Override
  public String getStudentProfilePdf(HttpServletResponse response, Long studentId) {

    JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Execute.getData());
    Map<String, Object> parameters = new HashMap<>();

    try {

      File fontFile = ResourceUtils.getFile("classpath:kalpurush.ttf");
      JRDesignStyle jrDesignStyle = new JRDesignStyle();
      jrDesignStyle.setDefault(true);
      jrDesignStyle.setPdfFontName(fontFile.getAbsolutePath());
      jrDesignStyle.setPdfEncoding("Identity-H");
      jrDesignStyle.setPdfEmbedded(true);

      File mainFile = ResourceUtils.getFile("classpath:teacher_class_schedule.jrxml");
      JasperReport mainReport = JasperCompileManager.compileReport(mainFile.getAbsolutePath());

      File subFile = ResourceUtils.getFile("classpath:teacher_class_schedule_detail.jrxml");
      JasperReport subReport = JasperCompileManager.compileReport(subFile.getAbsolutePath());
      parameters.put("subreportParameter", subReport);
      JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, dataSource);
      jasperPrint.addStyle(jrDesignStyle);
      response.setContentType("application/pdf");
      response.addHeader("Content-disposition", "attachment; filename=teacher_class_schedule.pdf");
      JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

    } catch (Exception exception) {
      exception.printStackTrace();
      return exception.getMessage();
    }
    return "report generated";
  }

  public List<Object[]> performJoinQuery() {
    /*
        String jpqlQuery = "SELECT student.firstName, course.name " +
                "FROM Student student " +
                "INNER JOIN CourseRating courseRating ON courseRating.courseRatingKey.studentEntity.studentId = student.studentId" +
                "INNER JOIN Course course ON course.courseId = courseRating.courseRatingKey.courseEntity.courseId";
    */

    String jpqlQuery = "SELECT student.firstName FROM Student";

    // TypedQuery<Object[]> query = entityManager.createQuery(jpqlQuery, Object[].class);

    //    return query.getResultList();
    return null;
  }

  @Override
  public String getStudentsPdf() {
    return "";
  }
}
