package com.xdbigdata.user_manage_admin.model.dto.student;

import lombok.Data;

@Data
public class ManagerStudentDto {

    private Long id;
    private String name;
    private String sn;
    private String collegeName;
    private String majorName;
    private Long grade;
    private String clazzName;


}