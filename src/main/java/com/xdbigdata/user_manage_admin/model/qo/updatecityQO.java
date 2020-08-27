package com.xdbigdata.user_manage_admin.model.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class updatecityQO {

    @ApiModelProperty("出发地(用于购买火车票)")
    private String departurePlace;

    @ApiModelProperty("到达站(用于购买火车票)")
    private String destinationPlace;

    @ApiModelProperty("学号")
    private String sn;
}
