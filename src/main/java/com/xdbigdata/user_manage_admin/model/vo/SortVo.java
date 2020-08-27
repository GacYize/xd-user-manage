package com.xdbigdata.user_manage_admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("排序信息")
public class SortVo {

    @ApiModelProperty("字段")
    private String field;

    @ApiModelProperty("排序方式：正序-asc，倒序-desc")
    private String sort;
}
