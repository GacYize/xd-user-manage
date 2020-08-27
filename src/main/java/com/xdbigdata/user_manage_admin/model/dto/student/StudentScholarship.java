package com.xdbigdata.user_manage_admin.model.dto.student;

import lombok.Data;

import java.util.Date;

@Data
public class StudentScholarship {

    private int id;

    private Date createDate;

    private String scholarshipName;

    private double money;

    private String schoolYear;
}
