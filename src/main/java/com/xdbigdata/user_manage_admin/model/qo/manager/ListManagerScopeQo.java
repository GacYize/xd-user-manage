package com.xdbigdata.user_manage_admin.model.qo.manager;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.xdbigdata.user_manage_admin.base.BasePageQo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("查询管理员管辖范围")
public class ListManagerScopeQo extends BasePageQo {

    private static final long serialVersionUID = -880641830241898722L;
    
    @ApiModelProperty(value = "管理人员id", required = true)
    @NotNull(message = "管理人员id不能为空")
    private Long managerId;
    
    @ApiModelProperty(value = "角色id", required = true)
    @NotNull(message = "角色id不能为空")
    private Long roleId;
	
    @ApiModelProperty(value = "管理人员姓名/学工号")
    private String filterValue;
    @ApiModelProperty(value = "学院")
	private String college;
    @ApiModelProperty(value = "专业")
	private String major;
    @ApiModelProperty(value = "班级")
	private String clazz;
    @ApiModelProperty(value = "年级")
	private String grade;

	public String getFilterValue() {
		if (StringUtils.isNotBlank(this.filterValue)) {
			if (this.filterValue.contains("%") || this.filterValue.contains("_")) {
				return this.filterValue.trim().replace("%", "[%]").replace("_", "[_]");
			} else {
				return this.filterValue.trim();
			}
		}
		return null;
	}
}