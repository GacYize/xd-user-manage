package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.annotation.NeedRole;
import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.model.dto.role.RoleDto;
import com.xdbigdata.user_manage_admin.model.dto.role.UserRoleDto;
import com.xdbigdata.user_manage_admin.mapper.AppMapper;
import com.xdbigdata.user_manage_admin.model.AppModel;
import com.xdbigdata.user_manage_admin.model.RoleModel;
import com.xdbigdata.user_manage_admin.model.qo.role.AddRoleQo;
import com.xdbigdata.user_manage_admin.model.qo.role.EditRoleQo;
import com.xdbigdata.user_manage_admin.model.qo.role.GrantAppRoleQo;
import com.xdbigdata.user_manage_admin.model.qo.role.ListRoleUserQo;
import com.xdbigdata.user_manage_admin.service.RoleService;
import com.xdbigdata.user_manage_admin.model.vo.role.AddRoleVo;
import com.xdbigdata.user_manage_admin.model.vo.role.EditRoleVo;
import com.xdbigdata.user_manage_admin.model.vo.role.GrantAppRoleVo;
import com.xdbigdata.user_manage_admin.model.vo.role.ListRoleUserPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @创建人:huangjianfeng
 * @简要描述:角色管理
 * @创建时间: 2018/10/25 10:49
 * @参数:
 * @返回:
 */
@RestController
@NeedRole(UserTypeConstant.ADMIN)
@RequestMapping("/api/role")
@Api(value = "角色管理控制层", tags = "角色管理")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AppMapper appMapper;

	@ApiOperation(value = "获取角色列表", notes = "角色列表接口")
	@GetMapping(value = "list", produces = "application/json;charset=UTF-8")
	public ResultBean<List<RoleDto>> listRole() {
		List<RoleDto> roleDtos = roleService.listRole();
		return new ResultBean<>(roleDtos);
	}

	@PreventRepeatSubmit
	@ApiOperation(value = "新增角色", notes = "新增角色接口")
	@PostMapping(value = "add", produces = "application/json;charset=UTF-8")
	public ResultBean<AddRoleVo> addRole(@Valid @RequestBody AddRoleQo addRoleQo) {
		AddRoleVo addRoleVo = roleService.add(addRoleQo);
		return new ResultBean<>(addRoleVo);
	}

	@PreventRepeatSubmit
	@ApiOperation(value = "授权app", notes = "授权app接口")
	@PostMapping(value = "grantApp", produces = "application/json;charset=UTF-8")
	public ResultBean<GrantAppRoleVo> grantAppRole(@Valid @RequestBody GrantAppRoleQo grantAppRoleQo) {
		GrantAppRoleVo grantAppRoleVo = roleService.grantAppRole(grantAppRoleQo);
		return new ResultBean<>(grantAppRoleVo);
	}

	@ApiOperation(value = "查询所有app集合", notes = "查询所有app的接口")
	@GetMapping(value = "getAllApps", produces = "application/json;charset=UTF-8")
	public ResultBean<List<AppModel>> getAllApps() {
		List<AppModel> apps = appMapper.selectAll();
		return new ResultBean<>(apps);
	}

	@PreventRepeatSubmit
	@ApiOperation(value = "冻结角色", notes = "冻结角色接口")
	@PostMapping(value = "freeze/{roleId}", produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "long", paramType = "path", required = true)
	public ResultBean freezeRole(@PathVariable("roleId") Long roleId) {
		String message = roleService.freezeRole(roleId);
		return ResultBean.createResultBean(message);
	}

	@PreventRepeatSubmit
	@ApiOperation(value = "解冻角色", notes = "冻结角色接口")
	@PostMapping(value = "thaw/{roleId}", produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "long", paramType = "path", required = true)
	public ResultBean thawRole(@PathVariable("roleId") Long roleId) {
		String message = roleService.thawRole(roleId);
		return ResultBean.createResultBean(message);
	}

	@PreventRepeatSubmit
	@ApiOperation(value = "修改角色", notes = "修改角色接口")
	@PostMapping(value = "edit", produces = "application/json;charset=UTF-8")
	public ResultBean<EditRoleVo> editRole(@Valid @RequestBody EditRoleQo editRoleQo) {
		EditRoleVo editRoleVo = roleService.editRole(editRoleQo);
		return new ResultBean<>(editRoleVo);
	}
	
	@ApiOperation(value = "查询名单", notes = "查询角色对应的名单接口")
	@PostMapping(value = "getUsersByRole", produces = "application/json;charset=UTF-8")
	public ResultBean<ListRoleUserPageVo> getUsersByRole(@Valid @RequestBody ListRoleUserQo listRoleUserQo) {
		ListRoleUserPageVo roleUserPageVo = roleService.getUsersByRole(listRoleUserQo);
		return new ResultBean<>(roleUserPageVo);
	}

	@PreventRepeatSubmit
	@ApiOperation(value = "冻结用户", notes = "冻结用户接口")
	@PostMapping(value = "freezeUser/{roleId}/{userId}", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "long", paramType = "path", required = true),
		@ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", paramType = "path", required = true)
	})
	public ResultBean freezeUser(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId) {
		String message = roleService.freezeUser(roleId, userId);
		return ResultBean.createResultBean(message);
	}

	@PreventRepeatSubmit
	@ApiOperation(value = "解冻用户", notes = "解冻用户接口")
	@PostMapping(value = "thawUser/{roleId}/{userId}", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "long", paramType = "path", required = true),
		@ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", paramType = "path", required = true)
	})
	public ResultBean thawUser(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId) {
		String message = roleService.thawUser(roleId, userId);
		return ResultBean.createResultBean(message);
	}

	@PreventRepeatSubmit
	@ApiOperation(value = "删除用户角色", notes = "删除用户角色接口")
	@PostMapping(value = "deleteUserRole/{roleId}/{userId}", produces = "application/json;charset=UTF-8")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "long", paramType = "path", required = true),
		@ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", paramType = "path", required = true)
	})
	public ResultBean deleteUserRole(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId) {
		String message = roleService.deleteUserRole(roleId, userId);
		return ResultBean.createResultBean(message);
	}
	
	@ApiOperation(value = "获取用户的所有角色", notes = "获取用户的所有角色接口")
	@GetMapping(value = "getUserRoles/{userId}", produces = "application/json;charset=UTF-8")
	@ApiImplicitParam(name = "userId", value = "用户id", dataType = "long", paramType = "path", required = true)
	public ResultBean<List<RoleModel>> getUserRoles(@PathVariable("userId") Long userId) {
		List<RoleModel> roles = roleService.getUserRoles(userId);
		return new ResultBean<>(roles);
	}
	
	@ApiOperation(value = "获取所有角色", notes = "获取所有角色接口")
	@GetMapping(value = "getAllRoles", produces = "application/json;charset=UTF-8")
	public ResultBean<List<RoleModel>> getAllRoles() {
		List<RoleModel> roles = roleService.getAllRoles();
		return new ResultBean<>(roles);
	}

	@ApiOperation(value = "获取所有未冻结的角色", notes = "获取所有角色接口")
	@GetMapping(value = "getUnFreezeRoles", produces = "application/json;charset=UTF-8")
	public ResultBean<List<RoleModel>> getUnFreezeRoles() {
		List<RoleModel> roles = roleService.getUnFreezeRoles();
		return new ResultBean<>(roles);
	}

	@PreventRepeatSubmit
	@ApiOperation(value = "修改用户角色", notes = "修改用户角色接口")
	@PostMapping(value = "updateUserRole", produces = "application/json;charset=UTF-8")
	public ResultBean updateUserRole(@Valid @RequestBody UserRoleDto userRoleDto) {
		String message = roleService.updateUserRole(userRoleDto);
		return ResultBean.createResultBean(message);
	}
}