package com.xdbigdata.user_manage_admin.model.dto.manager;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管辖范围
 * 
 * @author lshaci
 *
 */
@Data
@ApiModel("管理人员的管辖范围")
public class ManagerScopeDto {

	@ApiModelProperty(value = "管理人员id", required = true)
	@NotNull(message = "管理人员id不能为空")
	private Long managerId;
	
	@ApiModelProperty(value = "当前选择的角色id", required = true)
	@NotNull(message = "选择的角色id不能为空")
	private Long roleId;
	
	@ApiModelProperty(value = "管辖范围详情", required = true)
	@NotEmpty(message = "管辖范围详情不能为空")
	@Valid
	private Set<ManagerScopeDetailDto> scopeDetails;
}
