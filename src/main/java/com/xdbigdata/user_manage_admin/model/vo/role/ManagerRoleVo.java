package com.xdbigdata.user_manage_admin.model.vo.role;

import lombok.Data;

@Data
public class ManagerRoleVo {
	
	private Long id;
	private String name;
	private Integer valid;
	
	public ManagerRoleVo() {
	}

	public ManagerRoleVo(String name, Integer valid) {
		this.name = name;
		this.valid = valid;
	}
	
}
