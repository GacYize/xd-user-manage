package com.xdbigdata.user_manage_admin.model.domain;

import com.xdbigdata.user_manage_admin.util.DictionaryUtil;
import com.xdbigdata.user_manage_admin.util.OrganizationUtil;
import com.xdbigdata.user_manage_admin.util.OriginPlaceUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("学生的学籍信息")
@Table(name = "t_student_education")
public class StudentEducation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String sn;

    /**
     * 学院代码
     */
    @Column(name = "college_code")
    @ApiModelProperty("学院编号")
    private String collegeCode;

    /**
     * 获取学院名称
     */
    @ApiModelProperty("学院名称")
    public String getCollegeName() {
        return OrganizationUtil.getName(this.collegeCode);
    }

    /**
     * 专业代码
     */
    @Column(name = "major_code")
    @ApiModelProperty("专业名称")
    private String majorCode;

    /**
     * 获取专业名称
     */
    @ApiModelProperty("专业名称")
    public String getMajorName() {
        return OrganizationUtil.getName(this.majorCode);
    }

    /**
     * 班级代码
     */
    @Column(name = "class_code")
    @ApiModelProperty("班级编号")
    private String classCode;

    /**
     * 获取班级名称
     */
    @ApiModelProperty("班级名称")
    public String getClassName() {
        return OrganizationUtil.getName(this.classCode);
    }

    /**
     * 年级(t_grade)
     */
    @Column(name = "grade_id")
    @ApiModelProperty("年级id")
    private Long gradeId;

    /**
     * 获取年级
     */
    @ApiModelProperty("年级")
    public String getGrade() {
        return OrganizationUtil.getGrade(this.gradeId);
    }

    /**
     * 校区(t_campus)
     */
    @Column(name = "campus_id")
    private Long campusId;

    /**
     * 学制(年)
     */
    @Column(name = "school_length")
    @ApiModelProperty("学制(年)")
    private String schoolLength;

    /**
     * 预计毕业时间
     */
    @Column(name = "graduate_date")
    @ApiModelProperty(value = "预计毕业时间", required = true)
    @NotNull(message = "预计毕业时间未选择")
    private Date graduateDate;

    /**
     * 生源类型
     */
    @Column(name = "source_type")
    @ApiModelProperty("生源类型")
    private String sourceType;

    /**
     * 学籍状态(t_dictionary)
     */
    @Column(name = "education_status_id")
    @ApiModelProperty("学籍状态 - 数据字典id")
    private Long educationStatusId;

    /**
     * 获取学籍状态
     */
    @ApiModelProperty("学籍状态")
    public String getEducationStatus() {
        return DictionaryUtil.getName(this.educationStatusId);
    }

    /**
     * 学生状态(t_dictionary)
     */
    @Column(name = "education_id")
    @ApiModelProperty(value = "学生状态 - 数据字典id", required = true)
//    @NotNull(message = "学生类型未选择")
    private Long educationId;

    /**
     * 获取学生类型
     */
    @ApiModelProperty("学生类型")
    public String getEducation() {
        return DictionaryUtil.getName(this.educationId);
    }

    /**
     * 是否在籍
     */
    @ApiModelProperty("是否在籍")
    private Boolean absentee;

    /**
     * 是否在校
     */
    @Column(name = "at_school")
    @ApiModelProperty("是否在校")
    private Boolean atSchool;

    /**
     * 入学时间
     */
    @Column(name = "enter_date")
    @ApiModelProperty(value = "入学时间", required = true)
    @NotNull(message = "入学年月未选择")
    private Date enterDate;

    /**
     * 入学前户口所在地(t_origin_place)冒号分割
     */
    @Column(name = "domicile_place_code")
    @ApiModelProperty(value = "入学前户口所在地编码", required = true)
    @NotBlank(message = "入学前户口所在地未选择")
    private String domicilePlaceCode;

    /**
     * 获取入学前户口所在地
     */
    @ApiModelProperty("户口所在地")
    public String getDomicilePlace() {
        return OriginPlaceUtils.getName(this.domicilePlaceCode);
    }

    /**
     * 生源地(t_origin_place, parent_code为1)
     */
    @Column(name = "source_place_code")
    @ApiModelProperty(value = "生源地编号", required = true)
//    @NotBlank(message = "生源地未选择")
    private String sourcePlaceCode;

    /**
     * 获取生源地
     */
    @ApiModelProperty("生源地")
    public String getSourcePlace() {
        return OriginPlaceUtils.getName(this.sourcePlaceCode);
    }

    /**
     * 入学前就读中学
     */
    @Column(name = "high_school")
    @ApiModelProperty("入学前就读高中")
//    @NotBlank(message = "入学前就读高中未填写")
    private String highSchool;

    /**
     * 高考分数
     */
    @Column(name = "sat_scores")
    @ApiModelProperty("高考分数")
    private Double satScores;

    @Column(name = "is_school")
    @ApiModelProperty("学生状态")
    private String isSchool;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StudentEducation that = (StudentEducation) o;

        return new EqualsBuilder()
                .append(sn, that.sn)
                .append(collegeCode, that.collegeCode)
                .append(majorCode, that.majorCode)
                .append(classCode, that.classCode)
                .append(gradeId, that.gradeId)
                .append(schoolLength, that.schoolLength)
                .append(sourceType, that.sourceType)
                .append(educationStatusId, that.educationStatusId)
                .append(educationId, that.educationId)
                .append(absentee, that.absentee)
                .append(atSchool, that.atSchool)
                .append(isSchool, that.isSchool)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sn)
                .append(collegeCode)
                .append(majorCode)
                .append(classCode)
                .append(gradeId)
                .append(schoolLength)
                .append(sourceType)
                .append(educationStatusId)
                .append(educationId)
                .append(absentee)
                .append(atSchool)
                .append(isSchool)
                .toHashCode();
    }
}