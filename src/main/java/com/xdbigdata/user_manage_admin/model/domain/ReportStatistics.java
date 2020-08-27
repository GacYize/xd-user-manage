package com.xdbigdata.user_manage_admin.model.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Table(name = "t_report_statistics")
public class ReportStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String sn;

    /**
     * 学期
     */
    @ApiModelProperty("学期")
    private String semester;

    /**
     * 科目数
     */
    @ApiModelProperty("科目数")
    private Integer subjects;

    /**
     * 总学时
     */
    @ApiModelProperty("总学时")
    @Column(name = "hours_total")
    private Integer hoursTotal;

    /**
     * 总学分
     */
    @ApiModelProperty("总学分")
    @Column(name = "credit_total")
    private Double creditTotal;

    /**
     * 平均成绩
     */
    @ApiModelProperty("平均成绩")
    @Column(name = "average_score")
    private Double averageScore;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取学号
     *
     * @return sn - 学号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 设置学号
     *
     * @param sn 学号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 获取学期
     *
     * @return semester - 学期
     */
    public String getSemester() {
        return semester;
    }

    /**
     * 设置学期
     *
     * @param semester 学期
     */
    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * 获取科目数
     *
     * @return subjects - 科目数
     */
    public Integer getSubjects() {
        return subjects;
    }

    /**
     * 设置科目数
     *
     * @param subjects 科目数
     */
    public void setSubjects(Integer subjects) {
        this.subjects = subjects;
    }

    /**
     * 获取总学时
     *
     * @return hours_total - 总学时
     */
    public Integer getHoursTotal() {
        return hoursTotal;
    }

    /**
     * 设置总学时
     *
     * @param hoursTotal 总学时
     */
    public void setHoursTotal(Integer hoursTotal) {
        this.hoursTotal = hoursTotal;
    }

    /**
     * 获取总学分
     *
     * @return credit_total - 总学分
     */
    public Double getCreditTotal() {
        return creditTotal;
    }

    /**
     * 设置总学分
     *
     * @param creditTotal 总学分
     */
    public void setCreditTotal(Double creditTotal) {
        this.creditTotal = creditTotal;
    }

    /**
     * 获取平均成绩
     *
     * @return average_score - 平均成绩
     */
    public Double getAverageScore() {
        return averageScore;
    }

    /**
     * 设置平均成绩
     *
     * @param averageScore 平均成绩
     */
    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sn=").append(sn);
        sb.append(", semester=").append(semester);
        sb.append(", subjects=").append(subjects);
        sb.append(", hoursTotal=").append(hoursTotal);
        sb.append(", creditTotal=").append(creditTotal);
        sb.append(", averageScore=").append(averageScore);
        sb.append("]");
        return sb.toString();
    }
}