package com.xdbigdata.user_manage_admin.model.vo.student;

import lombok.Data;

import java.util.Date;

@Data
public class AddClazzStudentVo {

    private Long id;
    private String sn;
    private String name;
    private Integer gender;
    private Long nationId;
    private Integer politicalStatus;
    private Integer nativePlaceId;
    private Integer IDType;
    private String IDNumber;
    private Date birthDate;
    private String dietaryHabit;
    private Date enterDate ;
    private Date graduateDate;
    private String religion;
    private String hobby;
    private Long campusId;
    private String dorm;
    private String educationLevel;
    private String studentLength;
    private String phone;
    private String qq;
    private String wechat;
    private String email;
    private String homeContact;
    private String homePhone;
    private String homeAddress;
    private String homePostcode;
    private String picUrl;
    private Integer vailid;

}
