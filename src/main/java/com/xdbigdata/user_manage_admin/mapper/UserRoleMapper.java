package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.RoleModel;
import com.xdbigdata.user_manage_admin.model.UserRoleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper extends TKMapper<UserRoleModel> {

    UserRoleModel selectByUserIdAndRole(@Param("userId") Long  userId,@Param("roleId") Long  roleId);

    int insertUserRole(@Param("userId") Long  userId,@Param("roleId") Long  roleId);

    int deleteByUserId(@Param("userId") Long  userId);

    /**
     * 根据角色id和用户id修改用户角色的valid字段
     * 
     * @param userRoleModel
     */
	int updateValid(UserRoleModel userRoleModel);
	
	/**
	 * 根据角色id和用户id删除
	 * 
	 * @param userRoleModel
	 */
	int deleteByUserIdAndRoleId(UserRoleModel userRoleModel);

	/**
	 * 根据角色id和用户id查询
	 * 
	 * @param userRoleModel
	 * @return
	 */
	List<UserRoleModel> selectByUserIdAndRoleId(UserRoleModel userRoleModel);

	/**
	 * 根据用户id查询角色id集合
	 *
	 * @param userId
	 * @return
	 */
	List<Long> selectRoleIdByUserId(Long userId);
	
	/**
	 * 根据用户id获取该用户拥有的角色
	 * 
	 * @param userId
	 * @return
	 */
	List<RoleModel> selectUserRoles(Long userId);

	/**
	 * 批量添加到t_user_role
	 * @param userRoleModel
     */
	void addUserRole(@Param("userRoleModel")UserRoleModel userRoleModel);

	/**
	 * 根据用户id和角色id查询是否存在改角色
	 * @param userId
	 * @param roleId
	 */
	Integer selectRoleByUserAndRoleID(@Param("userId")Long userId,@Param("roleId") Long roleId);
}
