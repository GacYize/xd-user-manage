package com.xdbigdata.user_manage_admin.model.qo.student;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.xdbigdata.user_manage_admin.base.BasePageQo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListClazzStudentQo extends BasePageQo {

	private static final long serialVersionUID = -7724363935292883986L;
	
	@ApiModelProperty(value = "班级id", required = true)
	@NotNull(message = "班级id不能为空")
	private Long clazzId;
	
	@ApiModelProperty(value = "操作人姓名/学工号", required = false)
	private String filterValue;
	
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