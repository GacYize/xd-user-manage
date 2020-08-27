package com.xdbigdata.user_manage_admin.model.dto.operatelog;

import lombok.Data;

import java.util.Date;

@Data
public class OperateLogDto {

	private Long id;
	private Long operatorId;
	private String operatorName;
	private String roleName;
	private Date operateTime;
	private String module;
	private String detail;
}