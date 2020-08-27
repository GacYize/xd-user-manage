package com.xdbigdata.user_manage_admin.web.controller;


import com.xdbigdata.framework.common.model.PageResult;
import com.xdbigdata.framework.web.annotation.NeedRole;
import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.model.dto.manager.RejectDto;
import com.xdbigdata.user_manage_admin.enums.AuditStatus;
import com.xdbigdata.user_manage_admin.model.qo.XjydQo;
import com.xdbigdata.user_manage_admin.model.vo.QuzzyQueryVO;
import com.xdbigdata.user_manage_admin.model.vo.SubmitModifyDetailVo;
import com.xdbigdata.user_manage_admin.model.qo.student.StudentApplyListQo;
import com.xdbigdata.user_manage_admin.model.vo.TeachersVO;
import com.xdbigdata.user_manage_admin.model.vo.XjydVO;
import com.xdbigdata.user_manage_admin.service.StudentModifyAuditService;
import com.xdbigdata.user_manage_admin.service.StudentManageService;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentApplyListVo;
import com.xdbigdata.user_manage_admin.service.StudentService;
import com.xdbigdata.user_manage_admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/instructor")
@Api(value = "辅导员用户审核控制层", tags = "辅导员用户")
@NeedRole({UserTypeConstant.INSTRUCTOR,UserTypeConstant.COLLEGE,UserTypeConstant.SCHOOL,UserTypeConstant.CLASS_TEACHER})
public class InstructorController {

    @Autowired
    private StudentModifyAuditService auditService;

    @Autowired
    private StudentManageService studentManageService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @PostMapping("getAuditList")
    @ApiOperation("获取审核列表")
    public JsonResponse<PageResult<StudentApplyListVo>> getAuditList(@RequestBody StudentApplyListQo query) {
        PageResult<StudentApplyListVo> data = auditService.getAuditList(query);
        return JsonResponse.success(data);
    }

    @ApiOperation("根据审核表id查询详细")
    @GetMapping("findCommitHistoryInfoById/{id}")
    @ApiImplicitParam(value = "申请信息id", name = "id", paramType = "path", dataType = "long", required = true)
    public JsonResponse<SubmitModifyDetailVo> findCommitHistoryInfoById(@PathVariable("id") Long id) {
        SubmitModifyDetailVo vo = studentManageService.getSubmitModifyDetail(id);
        return JsonResponse.success(vo);
    }

    @PostMapping("pass/{id}")
    @ApiOperation("审批通过")
    @PreventRepeatSubmit
    @ApiImplicitParam(value = "申请信息id", name = "id", paramType = "path", dataType = "long", required = true)
    public JsonResponse<Object> pass(@PathVariable("id") Long id) {
        auditService.audit(id, AuditStatus.PASS, null);
        return JsonResponse.successMessage("审批同意成功");
    }

    @PostMapping("reject")
    @ApiOperation("驳回")
    @PreventRepeatSubmit
    public JsonResponse<Object> reject(@Valid @RequestBody RejectDto rejectDto) {
        auditService.audit(rejectDto.getId(), AuditStatus.REJECT, rejectDto.getReason());
        return JsonResponse.successMessage("驳回申请成功");
    }

    @GetMapping("/getInstructor/{organizationId}")
    @ApiOperation("通过组织机构id获取辅导员")
    public JsonResponse getInstructor(@PathVariable Long organizationId){
        List<TeachersVO> teacherVOList = userService.getInstructor(organizationId,null);
        return JsonResponse.success(teacherVOList);
    }

    @PostMapping("/fuzzyQueryUser")
    @ApiOperation("通过姓名或工号模糊匹配辅导员信息")
    public JsonResponse getInstructor(@RequestBody QuzzyQueryVO quzzyQueryVO){
        List<TeachersVO> teacherVOList = userService.fuzzyQueryUser(quzzyQueryVO);
        return JsonResponse.success(teacherVOList);
    }


    @PostMapping("/addInstructor")
    @ApiOperation("添加辅导员")
    public JsonResponse addInstructor(@RequestBody  TeachersVO teachersVO){
        userService.addInstructor(teachersVO);
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setCode(200);
        jsonResponse.setMessage("添加成功");
        jsonResponse.setStatus(true);
        return jsonResponse;
    }

    @PostMapping("/deleteInstructor")
    @ApiOperation("移除辅导员")
    public JsonResponse deleteInstructor(@RequestBody  TeachersVO teachersVO){
        userService.deleteInstructor(teachersVO);
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setCode(200);
        jsonResponse.setMessage("移除成功");
        jsonResponse.setStatus(true);
        return jsonResponse;
    }

    @PostMapping("/queryXJYDList")
    @ApiOperation("根据学号查询学生学籍异动情况")
    public JsonResponse<PageResult<XjydVO>> queryXJYDList(@RequestBody XjydQo xjydQo){
        PageResult<XjydVO> xjydVOPageResult = studentService.queryXJYDList(xjydQo);
        return JsonResponse.success(xjydVOPageResult);
    }


}
