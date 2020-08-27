package com.xdbigdata.user_manage_admin.service;


import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerScopeDto;
import com.xdbigdata.user_manage_admin.model.qo.manager.GrantJuridictionManagerQo;
import com.xdbigdata.user_manage_admin.model.qo.manager.ListManagerQo;
import com.xdbigdata.user_manage_admin.model.qo.manager.ListManagerScopeQo;
import com.xdbigdata.user_manage_admin.model.vo.manager.BasicInfoManagerVo;
import com.xdbigdata.user_manage_admin.model.vo.manager.ListManagerScopeVo;
import com.xdbigdata.user_manage_admin.model.vo.manager.ListManagerVo;

public interface ManagerService{

	/**
	 * 根据条件获取管理人员列表
	 * 
	 * @param listManagerQo
	 * @return
	 */
    ListManagerVo listManager(ListManagerQo listManagerQo);

    String freezeManager(Long id);

    String thawManager(Long id);

    String  grantManagerJuridiction(Long id,Long roleId, GrantJuridictionManagerQo grantJuridictionManagerQo);

    String removeManager(Long id);

    /**
     * 根据管理人员id查询详情
     * 
     * @param id 管理人员id
     * @return
     */
	BasicInfoManagerVo detailManager(Long id);

	/**
	 * 添加管理人员管辖范围
	 * 
	 * @param managerScopeDto
	 */
	void addManagerScope(ManagerScopeDto managerScopeDto);

	/**
	 * 查询管理人员管辖范围详情
	 * 
	 * @param listManagerScopeQo
	 * @return
	 */
	ListManagerScopeVo detailManagerScope(ListManagerScopeQo listManagerScopeQo);

	/**
	 * 根据id删除管辖范围
	 *
	 * @param id 除管辖范围id
	 */
	void deleteManagerScope(Long id);

	/**
	 * 修改管理人员管辖范围
	 *
	 * @param managerScopeDto
	 */
	void updateManagerScope(ManagerScopeDto managerScopeDto);

	/**
	 * 授权学生管辖范围
	 * @param managerScopeDto
     */
	void giveStudentPower(ManagerScopeDto managerScopeDto);

}