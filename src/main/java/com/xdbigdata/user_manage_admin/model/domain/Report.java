package com.xdbigdata.user_manage_admin.model.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

/**
 * @Author: ghj
 * @Date: 2019/12/3 20:05
 * @Version 1.0
 */
@Data
@ApiModel("学生成绩")
@Table(name = "t_report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 成绩代码
     */
    @Column(name = "report_code")
    private String reportCode;

    /**
     * 学号
     */
    private String sn;

    /**
     * 学期
     */
    private Integer semester;

    /**
     * 学时
     */
    private Integer hours;

    /**
     * 学分
     */
    private Integer credit;

    /**
     * 科目分数
     */
    private Double score;

    /**
     * 绩点
     */
    private Double gpa;

    /**
     * 状态（0无效|1有效）
     */
    private Integer status;

    /**
     * 课程代码
     */
    @Column(name = "curriculum_code")
    private String curriculumCode;

    /**
     * 课程名称
     */
    @Column(name = "curriculum_name")
    private String curriculumName;

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportCode='" + reportCode + '\'' +
                ", sn='" + sn + '\'' +
                ", semester=" + semester +
                ", hours=" + hours +
                ", credit=" + credit +
                ", score=" + score +
                ", gpa=" + gpa +
                ", status=" + status +
                ", curriculumCode='" + curriculumCode + '\'' +
                ", curriculumName='" + curriculumName + '\'' +
                '}';
    }
}
