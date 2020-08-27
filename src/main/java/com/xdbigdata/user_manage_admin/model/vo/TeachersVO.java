package com.xdbigdata.user_manage_admin.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeachersVO {


    @ApiModelProperty("组织机构id")
    private Long organizationId;

    @ApiModelProperty("工号")
    private String sn;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("教师id")
    private Long userId;
}
