package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.annotation.NeedRole;
import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.model.dto.campus.CampusDto;
import com.xdbigdata.user_manage_admin.model.qo.campus.AddCampusQo;
import com.xdbigdata.user_manage_admin.model.qo.campus.EditCampusQo;
import com.xdbigdata.user_manage_admin.service.CampusService;
import com.xdbigdata.user_manage_admin.model.vo.campus.AddCampusVo;
import com.xdbigdata.user_manage_admin.model.vo.campus.EditCampusVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/campus")
@NeedRole(UserTypeConstant.ADMIN)
@Api(value = "综合管理 - 校区管理 控制层", tags = "综合管理 - 校区管理")
public class CampusController {

    @Autowired
    private CampusService campusService;

    @PreventRepeatSubmit
    @ApiOperation("新增校区")
    @PostMapping(value = "add", produces = "application/json;charset=UTF-8")
    public ResultBean<AddCampusVo> addCampus(@Valid @RequestBody AddCampusQo addCampusQo) {
        AddCampusVo addCampusVo = campusService.addCampus(addCampusQo);
        return new ResultBean<>(addCampusVo);
    }

    @PreventRepeatSubmit
    @ApiOperation("编辑校区")
    @PostMapping(value = "edit", produces = "application/json;charset=UTF-8")
    public ResultBean<EditCampusVo> editCampus(@Valid @RequestBody EditCampusQo editCampusQo) {
        EditCampusVo editCampusVo = campusService.editCampus(editCampusQo);
        return new ResultBean<>(editCampusVo);
    }

    @NeedRole({ UserTypeConstant.STUDENT, UserTypeConstant.ADMIN })
    @ApiOperation("获取校区列表")
    @GetMapping(value = "list", produces = "application/json;charset=UTF-8")
    public ResultBean<List<CampusDto>> listCampus() {
        List<CampusDto> campusDtos = campusService.listCampus();
        return new ResultBean<>(campusDtos);
    }

}