package com.xdbigdata.user_manage_admin.model.dto.manager;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author:666
 * @Date:2019/8/13 10:11
 * @Version:1.0.0
 **/
@Data
public class InstructorManageStuInfDto implements Serializable {

    private String name;

    private Integer type;

    private String grade;
}
