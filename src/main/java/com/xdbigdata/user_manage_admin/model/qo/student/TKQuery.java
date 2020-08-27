package com.xdbigdata.user_manage_admin.model.qo.student;

import lombok.Data;

@Data
public class TKQuery {

    private String collegeCode;

    private String majorCode;

    private String classCode;

    private String gradeName;

    private String stuName;

    private String stuSn;

    private Integer page = 1;

    private Integer size = 10;
}
