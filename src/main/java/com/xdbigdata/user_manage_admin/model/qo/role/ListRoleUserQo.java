package com.xdbigdata.user_manage_admin.model.qo.role;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.xdbigdata.user_manage_admin.base.BasePageQo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("角色名单查询对象")
public class ListRoleUserQo extends BasePageQo {

    private static final long serialVersionUID = -878717676622826194L;
    
    @ApiModelProperty(value = "角色id", required = true)
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    
    @ApiModelProperty(value = "姓名/学工号")
    private String keyword;
    
	public String getKeyword() {
		if (StringUtils.isNotBlank(this.keyword)) {
			if (this.keyword.contains("%") || this.keyword.contains("_")) {
				return this.keyword.trim().replace("%", "[%]").replace("_", "[_]");
			} else {
				return this.keyword.trim();
			}
		}
		return null;
	}
}
