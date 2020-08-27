package com.xdbigdata.user_manage_admin.model.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel("用户角色管理")
public class UserRoleDto {

	@ApiModelProperty(value = "用户id", required = true)
	@NotNull(message = "用户id不能为空")
	private Long userId;
	@ApiModelProperty(value = "角色id集合", required = true)
	@NotEmpty(message = "角色id不能为空")
	private List<Long> roleIds;

}