package com.iwp.service.response;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ClassScheduleDetailResponse {

    private Long classPeriodId;
    private String classPeriodName;
    private String classPeriodNameBn;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long subjectId;
    private String subjectName;
    private Long employeeId;
    private String employeeName;
    private Boolean isBreak;
    private String note;
}
