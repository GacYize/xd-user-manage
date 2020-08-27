package com.xdbigdata.user_manage_admin.model.domain;

import com.xdbigdata.user_manage_admin.util.DictionaryUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("学生家庭信息")
@Table(name = "t_student_family")
public class StudentFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String sn;

    /**
     * 类型: 0:父母, 1:直系亲属, 2:其它重要联系人
     */
    @ApiModelProperty(value = "类型: 0:父母, 1:直系亲属, 2:其它重要联系人", required = true)
    @NotNull(message = "关系类型不能为空")
    private Integer type;

    /**
     * 关系(t_dictionary)
     */
    @Column(name = "relation_id")
    @ApiModelProperty("关系 - 数据字典id")
    private Long relationId;

    /**
     * 获取关系名称
     */
    @ApiModelProperty("关系")
    public String getRelation() {
        return DictionaryUtil.getName(this.relationId);
    }

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 联系电话
     */
    @ApiModelProperty("联系电话")
    private String phone;

    /**
     * 出生年月
     */
    @Column(name = "birth_date")
    @ApiModelProperty("出生年月")
    private Date birthDate;

    /**
     * 学历(t_dictionary)
     */
    @Column(name = "education_id")
    @ApiModelProperty("学历 - 数据字典id")
    private Long educationId;

    /**
     * 获取学历
     */
    @ApiModelProperty("学历")
    public String getEducation() {
        return DictionaryUtil.getName(this.educationId);
    }

    /**
     * 政治面貌(t_dictionary)
     */
    @Column(name = "politics_status_id")
    @ApiModelProperty("政治面貌 - 数据字典id")
    private Long politicsStatusId;

    /**
     * 获取政治面貌
     */
    @ApiModelProperty("政治面貌")
    public String getPoliticsStatus() {
        return DictionaryUtil.getName(this.politicsStatusId);
    }

    /**
     * 工作单位
     */
    @Column(name = "work_unit")
    @ApiModelProperty("工作单位")
    private String workUnit;

    /**
     * 职务
     */
    @ApiModelProperty("职务")
    private String duty;

    public StudentFamily setSn(String sn) {
        this.sn = sn;
        return this;
    }
}