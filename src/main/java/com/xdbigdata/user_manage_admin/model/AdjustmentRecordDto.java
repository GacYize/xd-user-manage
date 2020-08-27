package com.xdbigdata.user_manage_admin.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdjustmentRecordDto {

    @ApiModelProperty("调整后床位")
    private String afterPlace;

    @ApiModelProperty("学号")
    private String sn;

    @ApiModelProperty("所属公寓名称")
    private String name;
}
