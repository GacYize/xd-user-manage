package com.xdbigdata.user_manage_admin.model.qo.manager;
import org.apache.commons.lang3.StringUtils;

import com.xdbigdata.user_manage_admin.base.BasePageQo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("查询管理员列表")
public class ListManagerQo extends BasePageQo {

    private static final long serialVersionUID = -880641830241898722L;
    
    @ApiModelProperty(value = "管理人员姓名/学工号", required = false)
    private String filterValue;
    
    @ApiModelProperty(value = "角色id", required = false)
    private Long roleId;

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