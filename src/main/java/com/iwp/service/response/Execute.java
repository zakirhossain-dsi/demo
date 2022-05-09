package com.iwp.service.response;

import com.iwp.service.enums.Day;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Execute {
    public static void main(String[] args) throws JRException, FileNotFoundException {


        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(getData());

        File mainFile = ResourceUtils.getFile("classpath:teacher_class_schedule.jrxml");
        JasperReport mainReport = JasperCompileManager.compileReport(mainFile.getAbsolutePath());

        File subFile = ResourceUtils.getFile("classpath:teacher_class_schedule_detail.jrxml");
        JasperReport subReport = JasperCompileManager.compileReport(subFile.getAbsolutePath());


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("subreportParameter", subReport);

        JasperPrint jasperPrint = JasperFillManager.fillReport(mainReport, parameters, dataSource);

        System.out.println("Done");

    }

    public static List<ClassScheduleResponse> getData(){

        ClassScheduleResponse csr1 = new ClassScheduleResponse();
        csr1.setClassScheduleName("Test class shedule name");
        csr1.setClassSectionName("ABC class section");
        csr1.setDay(Day.MONDAY);
        csr1.setClassSectionName("Section-A");
        csr1.setShiftName("Morning");
        csr1.setAcademicVersionName("Bangla");
        csr1.setClassGroupName("General");
        csr1.setAcademicClassName("Nine");

        ClassScheduleDetailResponse csdr1 = new ClassScheduleDetailResponse();
        csdr1.setClassPeriodName("Day Period");
        csdr1.setStartTime(LocalTime.now());
        csdr1.setEndTime(LocalTime.now());
        csdr1.setSubjectName("Bangla");
        csdr1.setNote("This is a test node on Bangla");
        csdr1.setEmployeeName("Zakir");

        ClassScheduleDetailResponse csdr2 = new ClassScheduleDetailResponse();
        csdr2.setClassPeriodName("Day Period");
        csdr2.setStartTime(LocalTime.now());
        csdr2.setEndTime(LocalTime.now());
        csdr2.setSubjectName("Bangla");
        csdr2.setNote("This is a test node on Bangla");
        csdr2.setEmployeeName("Zakir");

        csr1.setClassScheduleDetails(List.of(csdr1, csdr2));

        ClassScheduleResponse csr2 = new ClassScheduleResponse();
        csr2.setClassScheduleName("Test class shedule name");
        csr2.setClassSectionName("ABC class section");
        csr2.setDay(Day.FRIDAY);
        csr2.setClassSectionName("Section-A");
        csr2.setShiftName("Morning");
        csr2.setAcademicVersionName("Bangla");
        csr2.setClassGroupName("General");
        csr2.setAcademicClassName("Nine");


        csr2.setClassScheduleDetails(List.of(csdr1, csdr2));

        return List.of(csr1, csr2);

    }
}
