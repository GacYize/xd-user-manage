package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.RoleModel;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.dto.role.ListRoleUserDto;
import com.xdbigdata.user_manage_admin.model.dto.role.RoleDto;
import com.xdbigdata.user_manage_admin.model.qo.role.ListRoleUserQo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends TKMapper<RoleModel> {

    /**
     * 查询角色，以及有权限的应用
     * 
     * @return
     */
    List<RoleDto> selectAllRoleWithApp();

    /**
     * 根据角色查询名单
     * 
     * @param listRoleUserQo
     * @return
     */
	List<ListRoleUserDto> selectUsersByRole(ListRoleUserQo listRoleUserQo);

    /**
     * 根据角色名称获取roleModel
     * @param name
     * @return
     */
    RoleModel findByName(@Param("name") String name);

    /**
     * 获取除学生外的所有角色
     * @param name
     * @return
     */
	List<RoleModel> selectManagerRoles(@Param("name") String name);

    /**
     * 根据用户id获取所含角色
     * @param userId
     * @return
     */
    List<RoleModel> findByUserId(@Param("userId") Long userId);

    /**
     * 根据sn获取用户角色
     * @param sn
     * @return
     */
    List<RoleModel> findBySn(@Param("sn")String sn);

    /**
     * 根据学生id查询辅导员姓名

     * @param id 学生的user id
     * @return 辅导员信息
     */
    UserModel selectInstructorByStudentId(@Param("id") Long id);

    /**
     * 根据学生id查询辅导员姓名
     * @return 辅导员信息
     */
    String selectInstructorByStudentIdInManageMent(@Param("sn") String sn);

}
