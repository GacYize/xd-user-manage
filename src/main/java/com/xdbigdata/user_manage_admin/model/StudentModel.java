package com.xdbigdata.user_manage_admin.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Table(name="t_student")
@Data
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "home_address")
    private String homeAddress;

    @Column(name = "home_postcode")
    private String homePostcode;

    @Column(name = "home_num")
    private Integer homeNum;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "enter_date")
    private Date enterDate;

    @Column(name = "graduate_date")
    private Date graduateDate;

    @Column(name = "dietary_habit")
    private String dietaryHabit;

    @Column(name = "religion")
    private String religion;

    @Column(name = "hobby")
    private String hobby;

    @Column(name = "dorm")
    private String dorm;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "grade")
    private String grade;

    @Column(name = "source_place")
    private String sourcePlace;


    @Column(name = "valid")
    private Integer valid;
    /**
     * 学籍状态
     * //学籍状态 0 正常 1 休学 2 退学 4 毕业
     */
    @Column(name = "status")
    private Integer status;

    @Column(name = "education_level")
    private String educationLevel;

    @Column(name = "student_length")
    private String studentLength;

    @Column(name = "home_contact")
    private String homeContact;

    @Column(name = "sn")
    private String sn;
    @Column(name = "go_place")
    private String goPlace;
    @Column(name = "arrive_place")
    private String arrivePlace;

    /**
     * 考生号
     */
    @Column(name = "candidate_number")
    private String candidateNumber;
    /**
     * 学生类别
     */
    @Column(name = "studentType")
    private Integer studentType;
    @Column(name = "studentTypeName")
    private String studentTypeName;

}