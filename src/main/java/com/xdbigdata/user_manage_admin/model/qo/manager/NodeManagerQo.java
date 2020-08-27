package com.xdbigdata.user_manage_admin.model.qo.manager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeManagerQo {

    @ApiModelProperty(value = "组织 id", required = true)
    private Long organizationId;
    @ApiModelProperty(value = "年级", required = false)
    private String grade;
    @ApiModelProperty(value = "学生id", required = false)
    private Long studentId;

}