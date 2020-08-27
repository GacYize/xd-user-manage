package com.xdbigdata.user_manage_admin.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
public class UpdatePasswordDto {

    @NotBlank(message = "新密码不能为空")
    @Length(min = 6, max = 16, message = "新密码长度为6-16位")
    @Pattern(regexp = "^[\\w]{6,16}$", message = "密码格式不正确")
    private String newPassword;

    @NotBlank(message = "旧密码不能为空")
    private String oldPassword;

    @ApiModelProperty(hidden = true)
    private String sn;
}
