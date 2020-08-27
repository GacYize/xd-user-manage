package com.xdbigdata.user_manage_admin.model.dto.manager;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管辖范围详情
 * 
 * @author lshaci
 */
@Data
public class ManagerScopeDetailDto {
	
	@ApiModelProperty(hidden = true)
	private Long id;
	@ApiModelProperty(hidden = true)
	private Long count;

	@NotNull(message = "学院id不能为空")
	private Long collegeId;
	private Long majorId;
	private Long clazzId;
	private Long studentId;
	private String grade;
	
	@NotEmpty(message = "学院名称不能为空")
	private String college;
	private String major;
	private String clazz;
	private String sn;
	private String name;
	
	@JsonIgnore
	public String getConcatStr() {
		return college + 
				(StringUtils.isNotBlank(major) ? major.trim() : "") + 
				(StringUtils.isNotBlank(clazz) ? clazz.trim() : "") +
				(StringUtils.isNotBlank(grade) ? grade.trim() : "") +
				(StringUtils.isNotBlank(sn) ? sn.trim() : "") +
				(StringUtils.isNotBlank(name) ? name.trim() : "");
	}
}
