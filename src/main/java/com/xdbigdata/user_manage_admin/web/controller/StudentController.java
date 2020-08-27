package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.qo.student.AddAndUpdateStudentQo;
import com.xdbigdata.user_manage_admin.model.qo.student.ListClazzStudentQo;
import com.xdbigdata.user_manage_admin.model.qo.student.MoveClazzStudentQo;
import com.xdbigdata.user_manage_admin.model.qo.student.TKQuery;
import com.xdbigdata.user_manage_admin.model.vo.student.ListClazzStudentVo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentVo;
import com.xdbigdata.user_manage_admin.service.StudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @创建人:huangjianfeng
 * @简要描述:学生相关的管理
 * @创建时间: 2018/10/25 10:49
 * @参数:
 * @返回:
 */
@Api(value = "学生用户", tags = "人员管理 - 学生用户")
@RestController
@RequestMapping("api/student/")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @CrossOrigin
    @PostMapping("listStu")
    public JsonResponse findByClassId(@RequestBody TKQuery tkQuery) {
        return JsonResponse.success(studentService.findSimpStuInf2TalkManage(tkQuery));
    }

    @ApiOperation(value = "获取班级下的学生列表", notes = "班级的学生列表接口")
    @PostMapping(value = "findByClassId", produces = "application/json;charset=UTF-8")
    public ResultBean<ListClazzStudentVo> findByClassId(@Valid @RequestBody ListClazzStudentQo listClazzStudentQo) {
        ListClazzStudentVo listClazzStudentVo = studentService.findByClassId(listClazzStudentQo);
        return new ResultBean<>(listClazzStudentVo);
    }

    @ApiOperation(value = "查看学生详细信息", notes = "查看学生详细信息")
    @GetMapping(value = "findById/{id}", produces = "application/json;charset=UTF-8")
    public ResultBean<StudentVo> findById(@PathVariable Long id) {
        StudentVo studentVo = studentService.findById(id);
        return new ResultBean<>(studentVo);
    }

    @ApiOperation(value = "在选中班级下新增/修改学生", notes = "班级下新增/修改学生接口")
    @PostMapping(value = "addOrUpdate", produces = "application/json;charset=UTF-8")
    public ResultBean addOrUpdate(@Valid @RequestBody AddAndUpdateStudentQo addAndUpdateStudentQo) {
        studentService.addOrUpdate(addAndUpdateStudentQo);
        return ResultBean.createResultBean("操作成功");
    }

    @PreventRepeatSubmit
    @ApiOperation(value = "批量新增学生")
    @RequestMapping(value = "/uploadStudentExcel/{classId}", method = RequestMethod.POST)
    public ResultBean uploadStudentExcel(@PathVariable("classId") Long classId, @RequestParam("file") MultipartFile file) throws Exception {
        List<String> studentSns = studentService.uploadStudentExcel(file, classId);
        return new ResultBean(studentSns);
    }

    @PreventRepeatSubmit
    @ApiOperation(value = "学生移动班级", notes = "学生移动班级接口")
    @PostMapping(value = "move", produces = "application/json;charset=UTF-8")
    public ResultBean editClazzStudent(@Valid @RequestBody MoveClazzStudentQo moveClazzStudentQo) {
        String message = studentService.moveClazzStudent(moveClazzStudentQo);
        return ResultBean.createResultBean(message);
    }

    @PreventRepeatSubmit
    @ApiOperation(value = "删除班级学生", notes = "删除班级学生接口")
    @GetMapping(value = "delete/{studentId}", produces = "application/json;charset=UTF-8")
    @ApiImplicitParam(name = "studentId", value = "学生id", dataType = "long", paramType = "path", required = true)
    public ResultBean removeClazzStudent(@PathVariable("studentId") Long studentId) {
        String message = studentService.removeClazzStudent(studentId);
        return ResultBean.createResultBean(message);
    }

    @ApiOperation(value = "授权学生中根据学号筛选学生", notes = "授权学生中根据学号筛选学生")
    @GetMapping(value = "findBySnAndOnlyStudentRole/{sn}", produces = "application/json;charset=UTF-8")
    public ResultBean findBySnAndOnlyStudentRole(@PathVariable String sn) {
        UserModel userModel = studentService.findBySnAndOnlyStudentRole(sn);
        return new ResultBean<>(userModel);
    }

}