package com.xdbigdata.user_manage_admin.model.vo.student;

import lombok.Data;

import java.util.Date;

/**
 * @author caijiang
 * @date 2018/10/29
 */
@Data
public class StudentVo {

    private Long id;

    //***********************基础信息*****************************
    /**
     * 学号
     */
    private String sn;
    /**
     * 姓名
     */
    private String name;
    /**
     * 头像路径
     */
    private String picUrl;
    /**
     *
     */
    private Integer gender;
    /**
     * 民族
     */
    private Long nationId;
    /**
     * 政治面貌
     */
    private Long politicalStatus;
    /**
     * 籍贯
     */
    private Long nativePlaceId;
    /**
     * 身份证号类型
     */
    private Integer idType;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 宗教信仰
     */
    private String religion;
    /**
     * 出生日期
     */
    private Date birthDate;
    /**
     * 饮食习惯
     */
    private String dietaryHabit;
    /**
     * 入学年月
     */
    private Date enterDate;
    /**
     * 预计毕业时间
     */
    private Date graduateDate;
    /**
     * 校区
     */
    private Long campusId;
    /**
     * 特长爱好
     */
    private String hobby;
    /**
     * 宿舍号
     */
    private String dorm;
    /**
     * 网登编号
     */
    private String netNumber;
    //***********************************所属组织信息******************************************
    /**
     * 所属学院
     */
    private String collegeName;
    /**
     * 专业
     */
    private String majorName;
    /**
     * 班级
     */
    private String className;
    /**
     * 年级
     */
    private String grade;
    /**
     * 学历层次
     */
    private String educationLevel;
    /**
     * 学制
     */
    private String studentLength;
    //********************************************本人联系信息******************************************
    /**
     * 电话
     */
    private String phone;
    /**
     * 微信
     */
    private String wechat;
    /**
     * 邮箱
     */
    private String email;
    /**
     * qq
     */
    private String qq;
    //***********************************************************家庭信息******************************
    /**
     * 家庭联系人
     */
    private String homeContact;
    /**
     * 家庭电话
     */
    private String homePhone;
    /**
     * 家庭地址
     */
    private String homeAddress;
    /**
     * 邮编
     */
    private String homePostcode;

}
