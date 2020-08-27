package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.annotation.NeedRole;
import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.qo.teacher.AddAndUpdateTeacherQo;
import com.xdbigdata.user_manage_admin.model.vo.teacher.TeacherVo;
import com.xdbigdata.user_manage_admin.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author caijiang
 * @date 2018/10/25
 */
@Slf4j
@RestController
@Api(value = "教师信息控制层", tags = "教师信息相关")
@NeedRole(UserTypeConstant.ADMIN)
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PreventRepeatSubmit
    @ApiOperation(value = "新增老师", notes = "新增老师接口")
    @PostMapping(value = "/addOrUpdate", produces = "application/json;charset=UTF-8")
    public ResultBean<UserModel> addOrUpdate(@RequestBody AddAndUpdateTeacherQo addTeacherQo) {
        UserModel userModel = teacherService.addOrUpdate(addTeacherQo);
        return new ResultBean<>(userModel);
    }

    @ApiOperation(value = "根据sn查看教师信息", notes = "根据sn查看教师信息")
    @PostMapping(value = "/findBySn/{sn}", produces = "application/json;charset=UTF-8")
    public ResultBean<TeacherVo> findBySn(@PathVariable String sn) {
        TeacherVo teacherVo = teacherService.findBySn(sn);
        return new ResultBean<>(teacherVo);
    }


    @GetMapping("listIns")
    public JsonResponse listIns(Long college, Integer page, Integer size, String search) {
        return JsonResponse.success(teacherService.listIns(college, search, page, size));
    }
}