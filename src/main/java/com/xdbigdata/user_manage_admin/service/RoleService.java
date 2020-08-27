package com.xdbigdata.user_manage_admin.service;

import com.xdbigdata.user_manage_admin.model.AppModel;
import com.xdbigdata.user_manage_admin.model.RoleModel;
import com.xdbigdata.user_manage_admin.model.dto.role.RoleDto;
import com.xdbigdata.user_manage_admin.model.dto.role.UserRoleDto;
import com.xdbigdata.user_manage_admin.model.qo.role.*;
import com.xdbigdata.user_manage_admin.model.vo.role.AddRoleVo;
import com.xdbigdata.user_manage_admin.model.vo.role.EditRoleVo;
import com.xdbigdata.user_manage_admin.model.vo.role.GrantAppRoleVo;
import com.xdbigdata.user_manage_admin.model.vo.role.ListRoleUserPageVo;

import java.util.List;

public interface RoleService{

	List<RoleDto> listRole();

	GrantAppRoleVo grantAppRole(GrantAppRoleQo grantAppRoleQo);

	/**
	 * 根据角色id冻结角色
	 * 
	 * @param roleId 角色id
	 * @return
	 */
	String freezeRole(Long roleId);

	/**
	 * 根据角色id解冻角色
	 * 
	 * @param roleId 角色id
	 * @return
	 */
	String thawRole(Long id);

	EditRoleVo editRole(EditRoleQo editRoleQo);

	/**
	 * 根据角色id查询未授权的app集合
	 * 
	 * @param roleId 角色id
	 * @return
	 */
	List<AppModel> getUnauthorizedApps(Long roleId);

	/**
	 * 添加角色
	 * 
	 * @param addRoleQo
	 * @return
	 */
	AddRoleVo add(AddRoleQo addRoleQo);

	/**
	 * 角色管理查看名单
	 * 
	 * @param listRoleUserQo
	 * @return
	 */
	ListRoleUserPageVo getUsersByRole(ListRoleUserQo listRoleUserQo);

	/**
	 * 冻结用户
	 * 
	 * @param roleId
	 * @param userId
	 * @return
	 */
	String freezeUser(Long roleId, Long userId);

	/**
	 * 解冻用户
	 * 
	 * @param roleId
	 * @param userId
	 * @return
	 */
	String thawUser(Long roleId, Long userId);

	/**
	 * 删除用户角色
	 * 
	 * @param roleId
	 * @param userId
	 * @return
	 */
	String deleteUserRole(Long roleId, Long userId);

	/**
	 * 获取除学生外的所有角色
	 * 
	 * @return
	 */
	List<RoleModel> getManagerRoles();

	/**
	 * 修改用户角色
	 * 
	 * @param userRoleDto
	 * @return
	 */
	String updateUserRole(UserRoleDto userRoleDto);

	/**
	 * 根据用户id获取该用户拥有的角色
	 * 
	 * @param userId
	 * @return
	 */
	List<RoleModel> getUserRoles(Long userId);

	/**
	 * 获取所有的角色
	 * 
	 * @return
	 */
	List<RoleModel> getAllRoles();

	/**
	 * 获取所有未冻结的角色
	 *
	 * @return
	 */
	List<RoleModel> getUnFreezeRoles();

}