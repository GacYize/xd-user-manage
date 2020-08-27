package com.xdbigdata.user_manage_admin.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Table(name="t_user")
@Data
public class UserModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "sn")
    private String sn;

    @Column(name = "email")
    private String email;

    @Column(name = "qq")
    private String qq;

    @Column(name = "wechat")
    private String wechat;

    @Column(name = "phone")
    private String phone;

    @Column(name = "standby_phone")
    private String standbyPhone;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "nation_id")
    private Long nationId;

    @Column(name = "political_status")
    private Integer politicalStatus;

    @Column(name = "native_place_id")
    private Long nativePlaceId;

    @Column(name = "pic_url")
    private String picUrl;

    @Column(name = "password")
    private String password;

    @Column(name = "short_phone")
    private String shortPhone;

    @Column(name = "valid")
    private Integer valid;

    /**
     * 身份证类型   1：身份证；2：护照
     */
    @Column(name = "id_type")
    private Integer idType;

    @Column(name = "campus_id")
    private Long campusId;
    @Column(name = "origin_code")
    private String originCode;
    @Column(name = "bank_number")
    private String bankNumber;
    @Column(name = "open_bank")
    private String openBank;

    @Column(name = "admin_org_code")
    private String adminOrgCode;

    /**
     * 老师最高学历
     */
    @Column(name = "teacher_top_education")
    private String teacherTopEducation;
    @Transient
    private String roleNames;
    /**
     * 籍贯
     */
    @Column(name = "jg")
    private String jg;

    @Transient
    private String organizationNames;


}