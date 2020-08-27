package com.xdbigdata.user_manage_admin.mapper;
import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.AppModel;

import java.util.List;

public interface AppMapper extends TKMapper<AppModel> {

	/**
	 * 根据角色id查询未授权的app集合
	 * 
	 * @param roleId 角色id
	 * @return
	 */
	List<AppModel> selectUnauthorizedApps(Long roleId);
	
}
