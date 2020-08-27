package com.xdbigdata.user_manage_admin.model.dto;

import lombok.Data;

@Data
public class UserManagementDto {
    private Long id;
    private Long userId;
    private Long organizationId;
    private Long studentId;
    private Long roleId;
    private String grade;
}