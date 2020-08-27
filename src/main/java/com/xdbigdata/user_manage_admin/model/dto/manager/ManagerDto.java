package com.xdbigdata.user_manage_admin.model.dto.manager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xdbigdata.user_manage_admin.model.vo.role.ManagerRoleVo;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class ManagerDto {
	
    private Long id;
    private String sn;
    private String name;
    
    @JsonIgnore
    private String roleNames;
    @JsonIgnore
    private String roleValids;
    @JsonIgnore
    private String userRoleValids;
    
    public List<ManagerRoleVo> getManagerRoles() {
    	List<ManagerRoleVo> managerRoles = new ArrayList<>();
    	if (StringUtils.isBlank(roleNames)) {
			return managerRoles;
		}
    	String[] roleNameArray = roleNames.trim().split(",");
    	String[] roleValidArray = roleValids.trim().split(",");
    	String[] userRoleValidArray = userRoleValids.trim().split(",");
    	for (int i = 0; i < roleNameArray.length; i++) {
    		String validStr = roleValidArray[i] + userRoleValidArray[i];
    		Integer valid = "11".equals(validStr) ? 1 : 0;
    		managerRoles.add(new ManagerRoleVo(roleNameArray[i], valid));
		}
    	return managerRoles;
    }

}