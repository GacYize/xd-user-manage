package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerScopeDetailDto;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerScopeDto;
import com.xdbigdata.user_manage_admin.model.UserManagementModel;
import com.xdbigdata.user_manage_admin.model.vo.ManageScope;
import com.xdbigdata.user_manage_admin.model.qo.manager.ListManagerScopeQo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserManagementMapper extends TKMapper<UserManagementModel> {

    int insertUserManagements(@Param("userManagementModels") List<UserManagementModel> userManagementModels);

    int deleteByUserId(@Param("userId") Long userId);

    /**
     * 查询管理人员管辖范围
     * 
     * @param listManagerScopeQo
     * @param listManagerScopeQo
     * @return
     */
	List<ManagerScopeDetailDto> selectManagerScopeDetail(ListManagerScopeQo listManagerScopeQo);

	/**
	 * 添加管理人员的管辖范围
	 * 
	 * @param managerScopeDto
	 */
	void addManagerScopeDetail(ManagerScopeDto managerScopeDto);

	/**
	 * 根据用户id和角色id查询管辖范围
	 *
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @return 管辖范围
	 */
	List<ManageScope> selectManageScope(@Param("userId") Long userId, @Param("roleId") Long roleId);
}