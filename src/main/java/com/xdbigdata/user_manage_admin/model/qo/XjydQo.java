package com.xdbigdata.user_manage_admin.model.qo;

import com.xdbigdata.framework.web.model.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class XjydQo extends PageQuery {

    @ApiModelProperty("学号")
    private String sn;
}
