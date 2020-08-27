package com.xdbigdata.user_manage_admin.model.dto.login;

import com.xdbigdata.user_manage_admin.enums.UserType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录用户信息
 */
@Data
public class LoginUserDto {
    @ApiModelProperty("用户id")
    private Long id;
    @ApiModelProperty("学号、学工号")
    private String sn;
    @ApiModelProperty("真实姓名")
    private String name;
    @ApiModelProperty("角色名称")
    private String roleName;
    private UserType userType;
    @ApiModelProperty(value = "用户类型", notes = "1：学生  2：学校用户 3：学院用户  4：年级辅导员 5:admin  ")
    private int type;
    @ApiModelProperty("角色id")
    private long roleId;
}
