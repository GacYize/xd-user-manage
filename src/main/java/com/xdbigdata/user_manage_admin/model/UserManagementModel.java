package com.xdbigdata.user_manage_admin.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "t_user_management")
@Data
public class UserManagementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "grade")
    private String grade;
}