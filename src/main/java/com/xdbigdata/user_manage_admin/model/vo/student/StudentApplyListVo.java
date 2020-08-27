package com.xdbigdata.user_manage_admin.model.vo.student;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 审核列表
 */
@Data
@ApiModel("审核列表信息")
public class StudentApplyListVo {

    @ApiModelProperty("审核id")
    private Long id;

    @ApiModelProperty("学号")
    private String sn;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("提交时间")
    private Date commitTime;

    @ApiModelProperty("审核人")
    private String instructorName;

    @ApiModelProperty("审核时间")
    private Date instructorTime;

    @ApiModelProperty("审核状态: -1:驳回, 0:待审核, 1:通过")
    private Integer instructorAudit;
}
