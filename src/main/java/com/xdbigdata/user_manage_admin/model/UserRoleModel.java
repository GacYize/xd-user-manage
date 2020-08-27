package com.xdbigdata.user_manage_admin.model;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Data
@Table(name="t_user_role")
public class UserRoleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "role_id")
    private Long roleId;
    
    private Integer valid;
    
    @Column(name = "join_time")
    private Date joinTime;

}