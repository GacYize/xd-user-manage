package com.xdbigdata.user_manage_admin.model.dto.role;

import com.xdbigdata.user_manage_admin.model.AppModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleDto {

	private Long id;
	private String name;
	private List<AppModel> apps;
	private Date createTime;
	private Integer valid;

}