package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.annotation.NeedRole;
import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.model.OrganizationModel;
import com.xdbigdata.user_manage_admin.model.qo.organization.AddOrganizationQo;
import com.xdbigdata.user_manage_admin.model.qo.organization.EditOrganizationQo;
import com.xdbigdata.user_manage_admin.service.OrganizationService;
import com.xdbigdata.user_manage_admin.model.vo.organization.OrganizationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @创建人:huangjianfeng
 * @简要描述:组织结构管理
 * @创建时间: 2018/10/25 10:49
 * @参数:
 * @返回:
 */
@RestController
@NeedRole({UserTypeConstant.ADMIN,UserTypeConstant.SCHOOL,UserTypeConstant.COLLEGE})
@RequestMapping("api/organization/")
@Api(value = "组织结构控制层", tags = "人员管理 - 组织管理")
public class OrganizationController{

    @Autowired
    private OrganizationService organizationService;

    @ApiOperation(value = "获取学校及所属学院信息", notes = "获取学校及所属学院信息")
    @GetMapping(value = "getSchoolOrganization", produces = "application/json;charset=UTF-8")
    public ResultBean<OrganizationVo> getSchoolOrganization() {
        OrganizationVo organizationVo = organizationService.getSchoolOrganization();
        return new ResultBean<>(organizationVo);
    }

    @ApiOperation(value = "根据父级id和组织类型获取组织", notes = "根据父级id和组织类型获取组织")
    @GetMapping(value = "getOrganization/{id}/{type}", produces = "application/json;charset=UTF-8")
    public ResultBean<OrganizationVo> getOrganization(@PathVariable Long id,@PathVariable Integer type) {
        OrganizationVo organizationVo = organizationService.getOrganization(id,type);
        return new ResultBean<>(organizationVo);
    }

    @PreventRepeatSubmit
    @ApiOperation(value = "编辑组织,只可修改名称和简称", notes = "编辑组织,只可修改名称和简称")
    @PostMapping(value = "update", produces = "application/json;charset=UTF-8")
    public ResultBean update(@RequestBody EditOrganizationQo editOrganizationQo) {
        organizationService.update(editOrganizationQo);
        return new ResultBean("修改成功");
    }

    @PreventRepeatSubmit
    @ApiOperation(value = "删除组织，验证是否有下属组织", notes = "删除组织，验证是否有下属组织")
    @GetMapping(value = "delete/{id}", produces = "application/json;charset=UTF-8")
    public ResultBean delete(@PathVariable Long id) {
        organizationService.delete(id);
        return new ResultBean("删除成功");
    }

    @PreventRepeatSubmit
    @ApiOperation(value = "新建组织机构", notes = "新建组织机构")
    @PostMapping(value = "add", produces = "application/json;charset=UTF-8")
    public ResultBean<OrganizationModel> add(@RequestBody AddOrganizationQo addOrganizationQo) {
        OrganizationModel organizationModel = organizationService.add(addOrganizationQo);
        return new ResultBean<>(organizationModel);
    }

}