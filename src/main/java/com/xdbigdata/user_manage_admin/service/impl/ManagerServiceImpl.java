package com.xdbigdata.user_manage_admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.user_manage_admin.annotation.OperateLog;
import com.xdbigdata.user_manage_admin.constant.CommonConstant;
import com.xdbigdata.user_manage_admin.constant.StatusConstant;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerDto;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerScopeDetailDto;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerScopeDto;
import com.xdbigdata.user_manage_admin.mapper.*;
import com.xdbigdata.user_manage_admin.model.*;
import com.xdbigdata.user_manage_admin.model.qo.manager.GrantJuridictionManagerQo;
import com.xdbigdata.user_manage_admin.model.qo.manager.ListManagerQo;
import com.xdbigdata.user_manage_admin.model.qo.manager.ListManagerScopeQo;
import com.xdbigdata.user_manage_admin.model.qo.manager.NodeManagerQo;
import com.xdbigdata.user_manage_admin.service.ManagerService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.util.masterworker.ManagerScopeVerifyTask;
import com.xdbigdata.user_manage_admin.util.masterworker.Master;
import com.xdbigdata.user_manage_admin.model.vo.manager.BasicInfoManagerVo;
import com.xdbigdata.user_manage_admin.model.vo.manager.ListManagerScopeVo;
import com.xdbigdata.user_manage_admin.model.vo.manager.ListManagerVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserManagementMapper userManagementMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Override
    public ListManagerVo listManager(ListManagerQo listManagerQo) {
    	PageHelper.startPage(listManagerQo.getPageNum(), listManagerQo.getPageSize());
    	Page<ManagerDto> managers = (Page<ManagerDto>) userMapper.selectManagers(listManagerQo, CommonConstant.STUDENT_ROLE_ID);
        return new ListManagerVo(managers, managers.getResult());
    }
    
    @Override
	public BasicInfoManagerVo detailManager(Long id) {
    	UserModel userModel = userMapper.selectByPrimaryKey(id);
    	if (userModel == null || userModel.getValid() == -1) {
			throw new BaseException("该管理人员不存在于系统中");
		}
    	return userMapper.selectManagerDetail(id, CommonConstant.STUDENT_ROLE_ID);
	}
    
	@Override
	public ListManagerScopeVo detailManagerScope(ListManagerScopeQo listManagerScopeQo) {
		PageHelper.startPage(listManagerScopeQo.getPageNum(), listManagerScopeQo.getPageSize());
    	Page<ManagerScopeDetailDto> managers = (Page<ManagerScopeDetailDto>) userManagementMapper.selectManagerScopeDetail(listManagerScopeQo);
        return new ListManagerScopeVo(managers, managers.getResult());
	}


	@Override
	@OperateLog(module = "人员管理")
	public void addManagerScope(ManagerScopeDto managerScopeDto) {
		Set<ManagerScopeDetailDto> newscopeDetails = managerScopeDto.getScopeDetails();
		if (CollectionUtils.isEmpty(newscopeDetails)) {
			throw new BaseException("管辖范围不能为空");
		}
		// 验证选择的管辖范围是否重复
		verifyScope(newscopeDetails, null, true);
        UserModel userModel = userMapper.selectByPrimaryKey(managerScopeDto.getManagerId());
        if (userModel == null) {
            throw new BaseException("用户不存在，设置失败");
        }
        ListManagerScopeQo query = new ListManagerScopeQo();
		query.setRoleId(managerScopeDto.getRoleId());
		query.setManagerId(managerScopeDto.getManagerId());
		List<ManagerScopeDetailDto> dbScopeDetails = userManagementMapper.selectManagerScopeDetail(query);
		if (CollectionUtils.isNotEmpty(dbScopeDetails)) {
			// 验证选择的管辖范围与历史管辖范围是否重复
			verifyScope(newscopeDetails, dbScopeDetails, false);
		}
		userManagementMapper.addManagerScopeDetail(managerScopeDto);
		
		ContextUtil.LOG_THREADLOCAL.set("设置用户:" + userModel.getName() + "管辖范围");
	}
	
	/**
	 * 验证管辖范围是否有重复
	 */
	private void verifyScope(Set<ManagerScopeDetailDto> newscopeDetails, List<ManagerScopeDetailDto> dbScopeDetails, boolean isAdd) {
		List<String> newScopes = newscopeDetails.stream().map(ManagerScopeDetailDto::getConcatStr).collect(Collectors.toList());
		List<String> allScopes;
		if (isAdd) {
			allScopes = newScopes;
		} else {
			allScopes = dbScopeDetails.stream().map(ManagerScopeDetailDto::getConcatStr).collect(Collectors.toList());
		}
		
		Master<Boolean> master = new Master<>();
		for (String scope : newScopes) {
			ManagerScopeVerifyTask task = new ManagerScopeVerifyTask(scope, allScopes, isAdd);
			master.submit(task);
		}
		master.execute();
		ConcurrentHashMap<String, Boolean> result = master.getResult();
		Set<Boolean> collect = result.values().stream().filter(b -> b).collect(Collectors.toSet());
		if (CollectionUtils.isEmpty(collect)) {
			log.info("管辖范围中无重复数据");
		} else {
			if (isAdd) {
				throw new BaseException("选择范围中有重复, 请检查后再试");
			} else {
				throw new BaseException("新增范围中与历史管辖范围有重复, 请检查后再试");
			}
		}
	}
	
	@Override
	@OperateLog(module = "人员管理")
	public void deleteManagerScope(Long id) {
		UserManagementModel userManagementModel = userManagementMapper.selectByPrimaryKey(id);
		if (userManagementModel == null) {
			throw new BaseException("管辖范围不存在, 操作失败");
		}
		userManagementMapper.deleteByPrimaryKey(id);
        UserModel userModel = userMapper.selectByPrimaryKey(userManagementModel.getUserId());
        String managementStr = "";
        if (userManagementModel.getOrganizationId() != null && userManagementModel.getStudentId() == null){
            OrganizationModel organizationModel = organizationMapper.findById(userManagementModel.getOrganizationId());
            managementStr = managementStr + organizationModel.getName();
        }
        if (userManagementModel.getGrade() != null){
            managementStr = managementStr + userManagementModel.getGrade();
        }
        if (userManagementModel.getStudentId() != null){
            UserModel student = userMapper.selectByPrimaryKey(userManagementModel.getStudentId());
            managementStr = managementStr + student.getName();
        }
		ContextUtil.LOG_THREADLOCAL.set("删除用户:" + userModel.getName() + "管辖范围:" + managementStr);
	}

	@Override
	@OperateLog(module = "人员管理")
	public void updateManagerScope(ManagerScopeDto managerScopeDto) {
        UserModel userModel = userMapper.selectByPrimaryKey(managerScopeDto.getManagerId());
		UserManagementModel condition = new UserManagementModel();
		condition.setUserId(managerScopeDto.getManagerId());
		condition.setRoleId(managerScopeDto.getRoleId());
		userManagementMapper.delete(condition);
		
		addManagerScope(managerScopeDto);
		ContextUtil.LOG_THREADLOCAL.set("修改用户:" + userModel.getName() + "管辖范围");
	}


	@Override
    @OperateLog(module = "人员管理")
    public String freezeManager(Long id) {
        if(id ==null || id.longValue()<1){
            throw new BaseException("参数 id 错误！");
        }
        UserModel userModel=userMapper.selectManager(id);
        if(userModel==null){
            throw new BaseException("记录不存在，请查证");
        }
        userModel.setValid(StatusConstant.STATUS_INVALID);
        userMapper.updateByPrimaryKeySelective(userModel);
        ContextUtil.LOG_THREADLOCAL.set("冻结用户:"+userModel.getSn());
        return "操作成功！！";
    }

    @Override
    @OperateLog(module = "人员管理")
    public String thawManager(Long id) {
        if(id ==null || id.longValue()<1){
            throw new BaseException("参数 id 错误！");
        }
        UserModel userModel=userMapper.selectManager(id);
        if(userModel==null){
            throw new BaseException("记录不存在，请查证");
        }
        userModel.setValid(StatusConstant.STATUS_VALID);
        userMapper.updateByPrimaryKeySelective(userModel);
        ContextUtil.LOG_THREADLOCAL.set("解冻用户:"+userModel.getSn());
        return "操作成功！！";
    }

    @Override
    @Transactional
    public String grantManagerJuridiction(Long id,Long roleId, GrantJuridictionManagerQo grantJuridictionManagerQo) {

        if(id ==null || id.longValue()<1){
            throw new BaseException("参数 id 错误！");
        }
        if(roleId ==null || roleId.longValue()<1){
            throw new BaseException("参数 roleId 错误！");
        }
        List<NodeManagerQo> nodeManagerQos=grantJuridictionManagerQo.getNodeManagerQos();
        if(CollectionUtils.isEmpty(nodeManagerQos)){
            throw new BaseException("必须选择管辖范畴！");
        }
        //先判断t_user_role
        UserRoleModel userRoleModel=userRoleMapper.selectByUserIdAndRole(id,roleId);
        if(userRoleModel==null){
            userRoleMapper.insertUserRole(id,roleId);
        }
        List<UserManagementModel> userManagementModels=Lists.newArrayList();
        UserManagementModel userManagementModel;


        for(NodeManagerQo nodeManagerQo:nodeManagerQos){
            userManagementModel=new UserManagementModel();
            userManagementModel.setUserId(id);
            userManagementModel.setOrganizationId(nodeManagerQo.getOrganizationId());
            userManagementModel.setStudentId(nodeManagerQo.getStudentId());
            userManagementModel.setRoleId(roleId);
            userManagementModel.setGrade(nodeManagerQo.getGrade());
            userManagementModels.add(userManagementModel);
        }
        userManagementMapper.deleteByUserId(id);
        userManagementMapper.insertUserManagements(userManagementModels);
        return "操作成功";
    }

    @Override
    @Transactional
    @OperateLog(module = "人员管理")
    public String removeManager(Long id) {
        UserModel userModel=userMapper.selectByPrimaryKey(id);
        String sn=StringUtils.trimToEmpty(userModel.getSn());
        //如果是学生
        StudentModel studentModel=studentMapper.selectByUserId(id);
        if(studentModel!=null){
            //删除用户与管理范畴关联
            userManagementMapper.deleteByUserId(id);
        }else{
            //删除用户
            userMapper.deleteByPrimaryKey(id);
            //删除用户与角色关联
            userRoleMapper.deleteByUserId(id);
            userManagementMapper.deleteByUserId(id);
            teacherMapper.deleteByUserId(id);
        }

        String detail="删除用户:"+sn;
        ContextUtil.LOG_THREADLOCAL.set(detail);
        return "删除成功！";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLog(module = "人员管理")
    public void giveStudentPower(ManagerScopeDto managerScopeDto) {
        UserModel userModel = userMapper.selectByPrimaryKey(managerScopeDto.getManagerId());
        //添加角色关联
        //查询是否存在改角色
        Integer num = userRoleMapper.selectRoleByUserAndRoleID(managerScopeDto.getManagerId(),managerScopeDto.getRoleId());
        if(num == null || num == 0){
            UserRoleModel userRoleModel = new UserRoleModel();
            userRoleModel.setUserId(managerScopeDto.getManagerId());
            userRoleModel.setRoleId(managerScopeDto.getRoleId());
            userRoleModel.setValid(1);
            userRoleModel.setJoinTime(new Date());
            userRoleMapper.addUserRole(userRoleModel);
        }
        //添加管辖权限
        addManagerScope(managerScopeDto);
        String detail="用户" + ContextUtil.getName() + "授权学生" + userModel.getName();
        ContextUtil.LOG_THREADLOCAL.set(detail);
    }
}