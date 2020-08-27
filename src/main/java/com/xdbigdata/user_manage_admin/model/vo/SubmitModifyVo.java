package com.xdbigdata.user_manage_admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("学生提交的修改记录")
public class SubmitModifyVo {

    @ApiModelProperty("审核表id")
    private Long auditId;

    @ApiModelProperty("提交时间")
    private Date commitTime;

    @ApiModelProperty("审核人")
    private String auditor;

    @ApiModelProperty("审核时间")
    private Date auditTime;

    @ApiModelProperty("审核状态: -1:驳回, 0:待审核, 1:通过")
    private Integer auditStatus;
}
