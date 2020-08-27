package com.xdbigdata.user_manage_admin.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.framework.utils.DateUtils;
import com.xdbigdata.framework.utils.constants.Constants;
import com.xdbigdata.user_manage_admin.annotation.OperateLog;
import com.xdbigdata.user_manage_admin.constant.CommonConstant;
import com.xdbigdata.user_manage_admin.constant.StatusConstant;
import com.xdbigdata.user_manage_admin.model.dto.role.ListRoleUserDto;
import com.xdbigdata.user_manage_admin.model.dto.role.RoleDto;
import com.xdbigdata.user_manage_admin.model.dto.role.UserRoleDto;
import com.xdbigdata.user_manage_admin.mapper.*;
import com.xdbigdata.user_manage_admin.model.*;
import com.xdbigdata.user_manage_admin.model.qo.role.*;
import com.xdbigdata.user_manage_admin.service.RoleService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.model.vo.role.AddRoleVo;
import com.xdbigdata.user_manage_admin.model.vo.role.EditRoleVo;
import com.xdbigdata.user_manage_admin.model.vo.role.GrantAppRoleVo;
import com.xdbigdata.user_manage_admin.model.vo.role.ListRoleUserPageVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private AppMapper appMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private AppRoleMapper appRoleMapper;
	
	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Autowired
    private UserManagementMapper userManagementMapper;

    @Override
    public List<RoleDto> listRole() {
        return roleMapper.selectAllRoleWithApp();
    }

    @Override
    @Transactional
    @OperateLog(module = "角色管理")
    public GrantAppRoleVo grantAppRole(GrantAppRoleQo grantAppRoleQo) {
    	Long roleId = grantAppRoleQo.getRoleId();
    	RoleModel roleModel = verifyRoleStatus(roleId);
    	
    	String ids = grantAppRoleQo.getAppIds().stream()
    			.filter(Objects::nonNull)
    			.map(id -> id + "")
    			.collect(Collectors.joining(","));
    	
    	List<AppModel> apps = appMapper.selectByIds(ids);
    	if (CollectionUtils.isEmpty(apps)) {
    		throw new BaseException("所授权的应用为空");
		}
    	
    	Set<AppRoleModel> appRoleModels = apps.stream().map(app -> {
    		AppRoleModel m = new AppRoleModel();
    		m.setAppId(app.getId());
    		m.setRoleId(roleId);
    		return m;
    	}).collect(Collectors.toSet());
    	
    	AppRoleModel condition =  new AppRoleModel();
    	condition.setRoleId(roleId);
    	appRoleMapper.delete(condition);
    	
    	appRoleMapper.insertList(new ArrayList<>(appRoleModels));
    	ContextUtil.LOG_THREADLOCAL.set("给予角色[" + roleModel.getName() + "]授权");
        return null;
    }

    @Override
	public List<AppModel> getUnauthorizedApps(Long roleId) {
    	verifyRole(roleId);
		return appMapper.selectUnauthorizedApps(roleId);
	}

	@Override
	@Transactional
	@OperateLog(module = "角色管理")
    public String freezeRole(Long roleId) {
		RoleModel roleModel = verifyRole(roleId);
		
		roleModel.setValid(StatusConstant.STATUS_INVALID);
		setUpdateInfo(roleModel);
		
		roleMapper.updateByPrimaryKeySelective(roleModel);
		ContextUtil.LOG_THREADLOCAL.set("冻结[" + roleModel.getName() + "]角色");
        return "角色冻结成功";
    }
	

	@Override
    @Transactional
    @OperateLog(module = "角色管理")
    public String thawRole(Long roleId) {
    	RoleModel roleModel = verifyRole(roleId);
		
		roleModel.setValid(StatusConstant.STATUS_VALID);
		setUpdateInfo(roleModel);
		
		roleMapper.updateByPrimaryKeySelective(roleModel);
		ContextUtil.LOG_THREADLOCAL.set("解冻[" + roleModel.getName() + "]角色");
        return "角色解冻成功";
    }
	
	@Override
	@Transactional
	@OperateLog(module = "角色管理")
	public AddRoleVo add(AddRoleQo addRoleQo) {
		String roleName = addRoleQo.getName();
		verifyRoleName(roleName);
		
    	RoleModel roleModel = new RoleModel();
    	roleModel.setName(addRoleQo.getName());
    	roleModel.setValid(StatusConstant.STATUS_VALID);
    	roleModel.setCreateUserId(ContextUtil.getId());
    	roleModel.setCreateTime(new Date());
    	
    	roleMapper.insertSelective(roleModel);
    	ContextUtil.LOG_THREADLOCAL.set("添加角色: " + addRoleQo.getName());
		return null;
	}

    @Override
    @Transactional
	@OperateLog(module = "角色管理")
    public EditRoleVo editRole(EditRoleQo editRoleQo) {
    	RoleModel roleModel = verifyRole(editRoleQo.getId());
    	if (CommonConstant.STUDENT_ROLE_ID.equals(editRoleQo.getId())) {
    		throw new BaseException("学生角色，不能进行操作");
		}
    	String oldName = roleModel.getName();
    	String roleName = editRoleQo.getName();
    	verifyRoleName(roleName);
    	roleModel.setName(roleName);
    	setUpdateInfo(roleModel);
    	
    	roleMapper.updateByPrimaryKeySelective(roleModel);
    	ContextUtil.LOG_THREADLOCAL.set("修改[" + oldName + "]角色名为：" + roleName);
        return null;
    }
    
    @Override
	public ListRoleUserPageVo getUsersByRole(ListRoleUserQo listRoleUserQo) {
    	try {
			Date startTime = listRoleUserQo.getStartTime();
			if (startTime != null) {
				String format = DateUtils.formatShortDate(startTime) + " 00:00:00";
				listRoleUserQo.setStartTime(Constants.LONG_DATE_FORMATTER.parse(format));
			}
			Date endTime = listRoleUserQo.getEndTime();
			if (endTime != null) {
				String format = DateUtils.formatShortDate(endTime) + " 23:59:59";
				listRoleUserQo.setEndTime(Constants.LONG_DATE_FORMATTER.parse(format));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	PageHelper.startPage(listRoleUserQo.getPageNum(), listRoleUserQo.getPageSize());
    	Page<ListRoleUserDto> page = (Page<ListRoleUserDto>) roleMapper.selectUsersByRole(listRoleUserQo);
    	
        return new ListRoleUserPageVo(page, page.getResult());
	}

	@Override
	@Transactional
	@OperateLog(module = "角色管理")
	public String freezeUser(Long roleId, Long userId) {
		UserModel user = verifyUser(userId);
		RoleModel roleModel = verifyRoleStatus(roleId);
		
		UserRoleModel userRoleModel = verifyUserRole(roleId, userId);
		userRoleModel.setValid(StatusConstant.STATUS_INVALID);
		
		userRoleMapper.updateValid(userRoleModel);
		ContextUtil.LOG_THREADLOCAL.set("冻结用户[" + user.getName() + "]角色：" + roleModel.getName());
		return "冻结用户角色成功";
	}

	@Override
	@Transactional
	@OperateLog(module = "角色管理")
	public String thawUser(Long roleId, Long userId) {
		UserModel user = verifyUser(userId);
		RoleModel roleModel = verifyRoleStatus(roleId);
		
		UserRoleModel userRoleModel = verifyUserRole(roleId, userId);
		userRoleModel.setValid(StatusConstant.STATUS_VALID);
		
		userRoleMapper.updateValid(userRoleModel);
		ContextUtil.LOG_THREADLOCAL.set("解冻用户[" + user.getName() + "]角色：" + roleModel.getName());
		return "解冻用户角色成功";
	}

	@Override
	@Transactional
	@OperateLog(module = "角色管理")
	public String deleteUserRole(Long roleId, Long userId) {
		UserModel user = verifyUser(userId);
		RoleModel roleModel = verifyRole(roleId);
		
		UserRoleModel userRoleModel = verifyUserRole(roleId, userId);
		
		userRoleMapper.deleteByUserIdAndRoleId(userRoleModel);
		
		// 删除角色对应的管辖范围
		UserManagementModel condition = new UserManagementModel();
		condition.setUserId(userId);
		condition.setRoleId(roleId);
		userManagementMapper.delete(condition);
		
		ContextUtil.LOG_THREADLOCAL.set("删除用户[" + user.getName() + "]角色：" + roleModel.getName());
		return "删除用户角色成功";
	}
	
	@Override
	public List<RoleModel> getManagerRoles() {
		return roleMapper.selectManagerRoles(CommonConstant.STUDENT_ROLE);
	}

	@Override
	@OperateLog(module = "人员管理")
	public String updateUserRole(UserRoleDto userRoleDto) {
		Long userId = userRoleDto.getUserId();
		UserModel user = verifyUser(userId);
		List<Long> addRoleIds = userRoleDto.getRoleIds();
		List<Long> dbRoleIds = userRoleMapper.selectRoleIdByUserId(userId);
		// 如果以前有学生角色，则现在也有学生角色（学生角色不能操作）
		if (dbRoleIds.contains(CommonConstant.STUDENT_ROLE_ID) && !addRoleIds.contains(CommonConstant.STUDENT_ROLE_ID) ) {
			addRoleIds.add(CommonConstant.STUDENT_ROLE_ID);
		}
		
		Collection<Long> needSave = CollectionUtils.subtract(addRoleIds, dbRoleIds);		
		Collection<Long> needDelete = CollectionUtils.subtract(dbRoleIds, addRoleIds);
		for (Long delete : needDelete) {
			deleteUserRole(delete, userId);
		}
		List<UserRoleModel> userRoleModels = new ArrayList<>();
		for (Long save : needSave) {
			UserRoleModel userRoleModel = new UserRoleModel();
			userRoleModel.setUserId(userId);
			userRoleModel.setRoleId(save);
			userRoleModel.setValid(StatusConstant.STATUS_VALID);
			userRoleModel.setJoinTime(new Date());
			
			userRoleModels.add(userRoleModel);
		}
		
		if (CollectionUtils.isNotEmpty(userRoleModels)) {
			userRoleMapper.insertList(userRoleModels);
		}
		
		ContextUtil.LOG_THREADLOCAL.set("修改用户[" + user.getName() + "]角色");
		return "修改成功";
	}

	@Override
	public List<RoleModel> getAllRoles() {
		return roleMapper.selectAll();
	}

    @Override
    public List<RoleModel> getUnFreezeRoles() {
        RoleModel query = new RoleModel();
        query.setValid(StatusConstant.STATUS_VALID);
        return roleMapper.select(query);
    }

	@Override
	public List<RoleModel> getUserRoles(Long userId) {
		return userRoleMapper.selectUserRoles(userId);
	}

	/**
     * 设置角色更新信息
     * 
     * @param roleModel
     */
    private void setUpdateInfo(RoleModel roleModel) {
    	roleModel.setUpdateTime(new Date());
		roleModel.setUpdateUserId(ContextUtil.getId());
    }
    
    /**
     * 验证id对应的角色是否有效
     * 
     * @param roleId 角色id
     * @return 角色有效则返回该角色
     */
    private RoleModel verifyRole(Long roleId) {
    	RoleModel roleModel = roleMapper.selectByPrimaryKey(roleId);
    	if (roleModel == null) {
			throw new BaseException("角色不存在，不能进行操作");
		}
    	
    	return roleModel;
    }
    
    /**
     * 根据id验证用户是否存在
     * 
     * @param userId
     * @return
     */
	private UserModel verifyUser(Long userId) {
		UserModel user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			throw new BaseException("用户不存在，不能进行操作");
		}
		if (user.getValid() == -1) {
			throw new BaseException("用户被删除，不能进行操作");
		}
		return user;
	}
    
    /**
     * 验证角色状态
     * 
     * @param roleId
     */
    private RoleModel verifyRoleStatus(Long roleId) {
    	RoleModel roleModel = verifyRole(roleId);
    	if (roleModel.getValid() == null || roleModel.getValid() == StatusConstant.STATUS_INVALID) {
    		throw new BaseException("当前角色被冻结，不能进行操作");
		}
    	return roleModel;
    }
    
    /**
     * 验证角色名是否重复
     * 
     * @param roleName 角色名
     */
    private void verifyRoleName(String roleName) {
    	RoleModel roleModel = new RoleModel();
    	roleModel.setName(roleName.trim());
    	List<RoleModel> dbRoleModels = roleMapper.select(roleModel);
    	if (CollectionUtils.isNotEmpty(dbRoleModels)) {
    		throw new BaseException("角色名称已存在");
		}
    }
    
    /**
     * 验证用户的角色
     * 
     * @param roleId
     * @param userId
     * @return
     */
    private UserRoleModel verifyUserRole(Long roleId, Long userId) {
		UserRoleModel userRoleModel = new UserRoleModel();
		userRoleModel.setRoleId(roleId);
		userRoleModel.setUserId(userId);
		
		List<UserRoleModel> dbUserRoleModels = userRoleMapper.selectByUserIdAndRoleId(userRoleModel);
		if (CollectionUtils.isEmpty(dbUserRoleModels)) {
			throw new BaseException("用户该角色不存在，不能进行操作");
		}
		if (dbUserRoleModels.size() > 1) {
			throw new BaseException("用户角色异常，请联系管理员");
		}
		return userRoleModel;
    }
}