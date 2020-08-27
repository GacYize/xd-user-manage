package com.xdbigdata.user_manage_admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("模糊匹配使用")
public class QuzzyQueryVO {

    @ApiModelProperty("姓名或学号或省或站点名称或简码")
    private String name;
}
