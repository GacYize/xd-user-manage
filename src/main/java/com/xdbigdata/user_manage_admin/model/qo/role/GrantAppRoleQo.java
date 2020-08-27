package com.xdbigdata.user_manage_admin.model.qo.role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GrantAppRoleQo {

    @ApiModelProperty(value = "角色id", required = true)
    @NotNull(message = "角色不能为空")
    private Long roleId;
    
    @ApiModelProperty(value = "应用id 集合", required = true)
    @NotEmpty(message = "授权应用不能为空")
    private List<Long> appIds;

}