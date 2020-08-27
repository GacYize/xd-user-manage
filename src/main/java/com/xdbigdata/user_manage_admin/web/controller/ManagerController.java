package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.annotation.NeedRole;
import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerScopeDto;
import com.xdbigdata.user_manage_admin.model.RoleModel;
import com.xdbigdata.user_manage_admin.model.qo.manager.GrantJuridictionManagerQo;
import com.xdbigdata.user_manage_admin.model.qo.manager.ListManagerQo;
import com.xdbigdata.user_manage_admin.model.qo.manager.ListManagerScopeQo;
import com.xdbigdata.user_manage_admin.service.ManagerService;
import com.xdbigdata.user_manage_admin.service.RoleService;
import com.xdbigdata.user_manage_admin.service.TeacherService;
import com.xdbigdata.user_manage_admin.model.vo.manager.BasicInfoManagerVo;
import com.xdbigdata.user_manage_admin.model.vo.manager.ListManagerScopeVo;
import com.xdbigdata.user_manage_admin.model.vo.manager.ListManagerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@NeedRole(UserTypeConstant.ADMIN)
@RequestMapping("/api/manager")
@Api(value = " 人员管理 - 管理人员控制层", tags = "人员管理 - 管理人员")
public class ManagerController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private TeacherService teacherService;

    @Autowired
    private ManagerService managerService;
    
    @GetMapping("getManagerRoles")
    @ApiOperation("获取除学生外的所有角色")
    public ResultBean<List<RoleModel>> getManagerRoles() {
    	List<RoleModel> datas = roleService.getManagerRoles();
    	return new ResultBean<>(datas);
    }

    @ApiOperation(value = "查询管理人员列表")
    @PostMapping(value = "list", produces = "application/json;charset=UTF-8")
    public ResultBean<ListManagerVo> listManagers(@RequestBody ListManagerQo listManagerQo) {
        ListManagerVo listManagerVo = managerService.listManager(listManagerQo);
        return new ResultBean<>(listManagerVo);
    }
    
    @ApiOperation(value = "查看管理人员详情")
    @GetMapping(value = "detail/{id}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "id", value = "管理人员id", dataType = "long", paramType = "path", required = true)
    public ResultBean<BasicInfoManagerVo> detailManager(@PathVariable("id") Long id) {
    	BasicInfoManagerVo basicInfoManagerVo = managerService.detailManager(id);
    	return new ResultBean<>(basicInfoManagerVo);
    }
    
    @ApiOperation(value = "查看管理人员管辖范围详情")
    @PostMapping(value = "detailManagerScope", produces = "application/json;charset=UTF-8")
    public ResultBean<ListManagerScopeVo> detailManagerScope(@Valid @RequestBody ListManagerScopeQo listManagerScopeQo) {
    	ListManagerScopeVo basicInfoManagerVo = managerService.detailManagerScope(listManagerScopeQo);
    	return new ResultBean<>(basicInfoManagerVo);
    }

	@PreventRepeatSubmit
	@ApiOperation(value = "添加管理人员管辖范围")
    @PostMapping(value = "addManagerScope", produces = "application/json;charset=UTF-8")
    public ResultBean addManagerScope(@Valid @RequestBody ManagerScopeDto managerScopeDto) {
    	managerService.addManagerScope(managerScopeDto);
    	return ResultBean.createResultBean("添加成功");
    }

	@PreventRepeatSubmit
	@ApiOperation(value = "修改管理人员管辖范围")
    @PostMapping(value = "updateManagerScope", produces = "application/json;charset=UTF-8")
    public ResultBean updateManagerScope(@Valid @RequestBody ManagerScopeDto managerScopeDto) {
    	managerService.updateManagerScope(managerScopeDto);
    	return ResultBean.createResultBean("修改成功");
    }

	@PreventRepeatSubmit
    @ApiOperation(value = "删除管辖范围")
    @PostMapping(value = "deleteManagerScope/{id}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "id", value = "管辖范围id", dataType = "long", paramType = "path", required = true)
    public ResultBean deleteManagerScope(@PathVariable("id") Long id) {
    	managerService.deleteManagerScope(id);
    	return ResultBean.createResultBean("删除成功");
    }
    
    

	@ApiOperation(value = "冻结管理人员", notes = "冻结管理人员接口", hidden = true)
	@PostMapping(value = "manager/freeze/{id}", produces = "application/json;charset=UTF-8")
	public ResultBean freezeManager(@ApiParam("相应的managerid") @PathVariable("id") Long id) {
		String message = managerService.freezeManager(id);
		return ResultBean.createResultBean(message);
	}

	@ApiOperation(value = "解冻管理人员", notes = "冻结管理人员接口", hidden = true)
	@PostMapping(value = "manager/thaw/{id}", produces = "application/json;charset=UTF-8")
	public ResultBean thawManager(@ApiParam("相应的manager id") @PathVariable("id") Long id) {
		String message = managerService.thawManager(id);
		return ResultBean.createResultBean(message);
	}

	@ApiOperation(value = "授权管理人员管理范围", notes = "授权管理人员管理范围接口", hidden = true)
	@PostMapping(value = "manager/grant_juridiction/{id}/{roleId}", produces = "application/json;charset=UTF-8")
	public ResultBean grantManagerJuridiction(
			@ApiParam("相应的manager id") @PathVariable("id") Long id,
			@ApiParam("相应的role id") @PathVariable("roleId") Long roleId,
			GrantJuridictionManagerQo grantJuridictionManagerQo) {
		String message = managerService.grantManagerJuridiction(id, roleId, grantJuridictionManagerQo);
		return ResultBean.createResultBean(message);
	}

    //分级查看 manager 下的 哪些节点是 勾选的了的
	@ApiOperation(value = "删除管理人员", notes = "删除管理人员接口", hidden = true)
	@PostMapping(value = "manager/remove/{id}", produces = "application/json;charset=UTF-8")
	public ResultBean removeManager(@ApiParam("相应的manager id") @PathVariable("id") Long id) {
		String message = managerService.removeManager(id);
		return ResultBean.createResultBean(message);
	}

    @ApiOperation(value = "授权学生", notes = "授权学生")
    @PostMapping(value = "giveStudentPower", produces = "application/json;charset=UTF-8")
    public ResultBean giveStudentPower(@Valid @RequestBody ManagerScopeDto managerScopeDto) {
        managerService.giveStudentPower(managerScopeDto);
        return new ResultBean<>();
    }
    
    @PostMapping("/batchGrantTeacher")
    @ApiOperation(value = "批量授权教师")
    public ResultBean batchGrantTeacher(@RequestParam("file") MultipartFile file, @RequestParam("role") Long[] roles) throws Exception {
		return teacherService.batchGrantTeacher(file, roles);
    }

}