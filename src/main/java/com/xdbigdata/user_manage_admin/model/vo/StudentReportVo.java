package com.xdbigdata.user_manage_admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ghj
 * @Date: 2019/12/4 13:20
 * @Version 1.0
 */
@Data
@ApiModel("学生成绩统计信息")
public class StudentReportVo {

    private Long id;
    @ApiModelProperty("学号")
    private String sn;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("学院")
    private String collage;
    @ApiModelProperty("专业")
    private String major;
    @ApiModelProperty("班级名称")
    private String className;
    @ApiModelProperty("学期")
    private Integer semester;
    @ApiModelProperty("科目数")
    private Integer subjects;
    @ApiModelProperty("总学时")
    private Integer hoursTotal;
    @ApiModelProperty("总学分")
    private Double creditTotal;
    @ApiModelProperty("平均成绩")
    private Double averageScore;
}
