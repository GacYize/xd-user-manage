package com.xdbigdata.user_manage_admin.model.qo.operatelog;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.xdbigdata.user_manage_admin.base.BasePageQo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("日志查询对象")
public class  ListOperateLogQo extends BasePageQo {

    private static final long serialVersionUID = -878717676622826194L;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    
    @ApiModelProperty(value = "操作模块")
    private String module;
    
    @ApiModelProperty(value = "操作人姓名/学工号")
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