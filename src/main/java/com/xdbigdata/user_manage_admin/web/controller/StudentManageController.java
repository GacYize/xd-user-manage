package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.annotation.NeedRole;
import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.framework.web.model.PageQuery;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.model.dto.student.StudentModifyDto;
import com.xdbigdata.user_manage_admin.model.qo.updatecityQO;
import com.xdbigdata.user_manage_admin.model.vo.StudentInfoVo;
import com.xdbigdata.user_manage_admin.model.vo.SubmitModifyDetailVo;
import com.xdbigdata.user_manage_admin.model.vo.SubmitModifyVo;
import com.xdbigdata.user_manage_admin.service.StudentManageService;
import com.xdbigdata.user_manage_admin.service.StudentService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ghj
 * @date 2019/2/18
 */
@Slf4j
@RestController
@NeedRole(UserTypeConstant.STUDENT)
@RequestMapping("api/studentManage")
@Api(value = "学生模块控制层", tags = "学生模块")
public class StudentManageController {

    @Autowired
    private StudentManageService studentManageService;

    @Autowired
    private StudentService studentService;


    @ApiOperation("获取当前登录的学生信息")
    @GetMapping("getLoginStudentInfo")
    public JsonResponse<StudentInfoVo> getLoginStudentInfo() {
        String sn = ContextUtil.getSn();
        StudentInfoVo studentInfoVo = studentManageService.getStudentInfo(sn);
        return JsonResponse.success(studentInfoVo);
    }

    @PreventRepeatSubmit
    @ApiOperation("提交修改信息")
    @PostMapping("commitUpdateInfo")
    public JsonResponse<Object> commitUpdateInfo(@Valid @RequestBody StudentModifyDto studentModifyDto) {
        studentManageService.commitUpdateInfo(studentModifyDto);
        return JsonResponse.successMessage("提交成功");
    }

    @ApiOperation("获取提交历史列表")
    @GetMapping("getCommitHistoryList")
    public JsonResponse<List<SubmitModifyVo>> getCommitHistoryList(PageQuery query) {
        List<SubmitModifyVo> data = studentManageService.getCommitHistoryList(query);
        return JsonResponse.success(data);
    }

    @ApiOperation("根据审核表id查询详细")
    @GetMapping("findCommitHistoryInfoById/{id}")
    public JsonResponse<SubmitModifyDetailVo> findCommitHistoryInfoById(@PathVariable("id") Long id) {
        SubmitModifyDetailVo data = studentManageService.getSubmitModifyDetail(id);
        return JsonResponse.success(data);
    }

    @PostMapping("/updateCityBySn")
    @ApiOperation("修改学生站点信息")
    public JsonResponse updateCityBySn(@RequestBody updatecityQO updatecityQO) {
        studentService.updateCityBySn(updatecityQO);
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setCode(200);
        jsonResponse.setMessage("修改成功");
        jsonResponse.setStatus(true);
        return jsonResponse;
    }


}
