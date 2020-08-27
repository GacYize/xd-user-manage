package com.xdbigdata.user_manage_admin.model.qo.campus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class EditCampusQo {

    @ApiModelProperty(value = "校区编号", required = true)
    @NotNull(message = "校区编号不能为空")
    private Long id;
    
    @ApiModelProperty(value = "校区名称", required = true)
    @NotBlank(message = "校区名称不能为空")
    @Length(max = 10, message = "校区名称长度不能超过10个字符")
    private String name;
    
    @ApiModelProperty(value = "校区地址", required = true)
    @NotBlank(message = "校区地址不能为空")
    @Length(max = 30, message = "校区地址长度不能超过30个字符")
    private String address;
}