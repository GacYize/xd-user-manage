package com.xdbigdata.user_manage_admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="t_operate_log")
public class OperateLogModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module")
    private String module;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "operator_id")
    private Long operatorId;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "operate_time")
    private Date operateTime;

    @Column(name = "detail")
    private String detail;




}