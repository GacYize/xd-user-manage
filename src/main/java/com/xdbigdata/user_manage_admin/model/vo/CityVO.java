package com.xdbigdata.user_manage_admin.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CityVO {

    @ApiModelProperty("站点id")
    private Long id;

    @ApiModelProperty("省")
    private String province_name;

    @ApiModelProperty("站点名称")
    private String name;

    @ApiModelProperty("站点简码")
    private String code;
}
