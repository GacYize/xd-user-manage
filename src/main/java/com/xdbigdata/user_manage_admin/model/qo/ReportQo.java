package com.xdbigdata.user_manage_admin.model.qo;

import com.xdbigdata.framework.web.model.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: ghj
 * @Date: 2019/12/4 11:15
 * @Version 1.0
 */
@Data
@ApiModel("学生成绩检索条件")
public class ReportQo extends PageQuery{
    @ApiModelProperty("学院代码")
    private String collageCode;
    @ApiModelProperty("专业代码")
    private String majorCode;
    @ApiModelProperty("班级代码")
    private String classCode;
    @ApiModelProperty("学期")
    private Integer semester;
    @ApiModelProperty("学号或者姓名")
    private String snOrName;

    private String loginSn;

    private Long loginRoleId;
}
