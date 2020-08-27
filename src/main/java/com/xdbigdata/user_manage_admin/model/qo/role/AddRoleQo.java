package com.xdbigdata.user_manage_admin.model.qo.role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
public class AddRoleQo {

    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Length(max = 10, message = "角色名称长度不能超过10个字符")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{1,10}$", message = "角色名称只能为汉字")
    private String name;

}