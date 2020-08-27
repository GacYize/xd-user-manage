package com.xdbigdata.user_manage_admin.model.oracle;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 教务系统视图中的学生信息
 */
@Data
public class StudentRemote {

    // 学号 XH
    private String sn;
    // 姓名 XM
    private String name;
    // 性别码（1男2女） XBM
    private Integer genderType;
    // 入学年月20150901 RXNY
    private String enterDate;
    // 学院编号 DWH
    private String collegeCode;
    // 学院名称 DWMC
    private String college;
    // 专业编码 ZYM
    private String majorCode;
    // 专业名称 ZYMC
    private String major;
    // 班级编码 SZBH
    private String classCode;
    // 班级名称 BJ
    private String clazz;
    // 学生类别 XSLB (生源类别)     -> 为null = 本科统招
    private String sourceType;
    // 出生日期19970613 CSRQ
    private String brithDate;
    // 家庭地址 JTZZ
    private String homeAddress;
    // 银行卡号 YHKH
    private String bankCard;
    // 身份证号 SFZJH
    private String idNumber;
    // 学生状态 XSZT
    private String educationStatus;
    // 学制 XZ
    private String schoolLength;
    // 电话 MOBILE
    private String phone;
    // 邮箱 EMAIL
    private String email;
    // 是否在校（是、否） SFZX
    private String atSchool;
    // 是否有学籍（有、无） SFYXJ
    private String absentee;
    // 年级 SZNJ
    private String grade;
    /**
     * 验证组织机构信息是否有效
     *
     * @param studentRemote
     * @return
     */
    public static boolean verify(StudentRemote studentRemote) {
        if (Objects.isNull(studentRemote)) {
            return false;
        }

        if (StringUtils.isBlank(studentRemote.getCollege()) || StringUtils.isBlank(studentRemote.getCollegeCode()) ||
                StringUtils.isBlank(studentRemote.getMajor()) || StringUtils.isBlank(studentRemote.getMajorCode()) ||
                StringUtils.isBlank(studentRemote.getClazz()) || StringUtils.isBlank(studentRemote.getClassCode())) {
            return false;
        }
        return true;
    }

    /**
     * 获取学生状态 0 正常 1 休学 2 退学 4 毕业
     * this.status: 在读, 其它
     * @return
     */
    public Integer getStudentStatus() {
        if (StringUtils.isBlank(this.educationStatus)) {
            return null;
        }
        Integer studentStatus = null;
        switch (this.educationStatus.trim()) {
            case "正常": studentStatus = 0; break;
            case "在读": studentStatus = 0; break;
            case "休学": studentStatus = 1; break;
            case "退学": studentStatus = 2; break;
            case "毕业": studentStatus = 4; break;
            case "其它": studentStatus = -1; break;
        }
        return studentStatus;
    }

}
