package com.xdbigdata.user_manage_admin.model.dto.role;

import java.util.Date;

import lombok.Data;

@Data
public class ListRoleUserDto {

	private Long roleId;
	
	private Long userId;
	
	private String name;
	
	private String sn;
	
	private Integer valid;
	
	private Date joinTime;
}
