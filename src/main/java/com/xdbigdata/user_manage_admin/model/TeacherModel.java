package com.xdbigdata.user_manage_admin.model;

import lombok.Data;

import javax.persistence.*;

@Table(name="t_teacher")
@Data
public class TeacherModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department")
    private String department;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "valid")
    private Integer valid;

}