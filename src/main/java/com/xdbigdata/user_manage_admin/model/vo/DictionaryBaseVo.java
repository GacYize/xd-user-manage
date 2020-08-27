package com.xdbigdata.user_manage_admin.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ghj
 * @Description
 * @date 2019/11/7 18:21
 */
@ApiModel("字典基础对象")
@Data
public class DictionaryBaseVo {
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("代码")
    private Integer code;
}
