package com.xdbigdata.user_manage_admin.model.domain;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "t_student_modify_audit")
public class StudentModifyAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学号
     */
    private String sn;

    /**
     * 修改的基本信息
     */
    @Column(name = "basic_modify")
    private String basicModify;

    /**
     * 获取修改后的学生基本信息
     */
    public StudentBasic getStudentBasicModify() {
        if (StringUtils.isBlank(this.basicModify)) {
            return null;
        }

        return JSON.parseObject(this.basicModify, StudentBasic.class);
    }

    /**
     * 历史的基本信息
     */
    @Column(name = "basic_history")
    private String basicHistory;

    /**
     * 获取历史的学生基本信息
     */
    public StudentBasic getStudentBasicHistory() {
        if (StringUtils.isBlank(this.basicHistory)) {
            return null;
        }

        return JSON.parseObject(this.basicHistory, StudentBasic.class);
    }

    /**
     * 修改的联系信息
     */
    @Column(name = "contact_modify")
    private String contactModify;

    /**
     * 获取修改后的学生基本信息
     */
    public StudentContact getStudentContactModify() {
        if (StringUtils.isBlank(this.contactModify)) {
            return null;
        }

        return JSON.parseObject(this.contactModify, StudentContact.class);
    }

    /**
     * 历史的联系信息
     */
    @Column(name = "contact_history")
    private String contactHistory;

    /**
     * 获取历史的学生联系信息
     */
    public StudentContact getStudentContactHistory() {
        if (StringUtils.isBlank(this.contactHistory)) {
            return null;
        }

        return JSON.parseObject(this.contactHistory, StudentContact.class);
    }

    /**
     * 修改的学籍信息
     */
    @Column(name = "education_modify")
    private String educationModify;

    /**
     * 获取修改后的学生学籍信息
     */
    public StudentEducation getStudentEducationModify() {
        if (StringUtils.isBlank(this.educationModify)) {
            return null;
        }

        return JSON.parseObject(this.educationModify, StudentEducation.class);
    }

    /**
     * 历史的学籍信息
     */
    @Column(name = "education_history")
    private String educationHistory;

    /**
     * 获取历史的学生学籍信息
     */
    public StudentEducation getStudentEducationHistory() {
        if (StringUtils.isBlank(this.educationHistory)) {
            return null;
        }

        return JSON.parseObject(this.educationHistory, StudentEducation.class);
    }

    /**
     * 修改的家庭信息
     */
    @Column(name = "families_modify")
    private String familiesModify;

    /**
     * 获取学生家庭信息
     */
    public List<StudentFamily> getStudentFamilies() {
        if (StringUtils.isBlank(this.familiesModify)) {
            return null;
        }

        return JSON.parseArray(this.familiesModify, StudentFamily.class);
    }

    /**
     * 提交时间
     */
    @Column(name = "submit_time")
    private Date submitTime;

    /**
     * 审核人sn
     */
    @Column(name = "audit_sn")
    private String auditSn;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    private Date auditTime;

    /**
     * 审核状态: -1:驳回, 0:待审核, 1:通过
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 审核意见
     */
    @Column(name = "audit_comment")
    private String auditComment;

}