package com.xdbigdata.user_manage_admin.model.dto.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author huyuanjia
 * @date 2018/12/5 10:57
 */
@Data
@ApiModel("返回列表")
public class ListVO {

    @ApiModelProperty(value = "总记录数")
    private Integer total;

    @ApiModelProperty(value = "列表")
    private List list;


}
