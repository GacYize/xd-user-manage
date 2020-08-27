package com.xdbigdata.user_manage_admin.model.dto.student;

import lombok.Data;

@Data
public class StuSimInfDto {

    /**
     * 学生姓名
     */
    private String stuName;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 学号
     */
    private String sn;

    /**
     * 年级
     */
    private String grade;

    /**
     * 电话号码
     */
    private String phone;
}
