package com.xdbigdata.user_manage_admin.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @auther caijiang
 * @data 2018/11/9
 */
@Table(name="t_grade")
@Data
public class GradeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer valid;

}
