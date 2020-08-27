package com.xdbigdata.user_manage_admin.model.qo.manager;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GrantJuridictionManagerQo {

    @ApiModelProperty(value = "叶子节点的集合", required = true)
    private List<NodeManagerQo> nodeManagerQos;

}