package com.iwp.service.response;

import com.iwp.service.enums.Day;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClassScheduleResponse {

    List<ClassScheduleDetailResponse> classScheduleDetails = new ArrayList<>();

    private Long id;
    private String classScheduleName;
    private String classScheduleNameBn;
    private Long instituteId;
    private Day day;
    private Long classSectionId;
    private String classSectionName;
    private Long shiftId;
    private String shiftName;
    private Long academicVersionId;
    private String academicVersionName;
    private Long classGroupId;
    private String classGroupName;
    private Long academicClassId;
    private String academicClassName;
    private Integer recordVersion;
}
