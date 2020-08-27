package com.xdbigdata.user_manage_admin.web.controller;

import com.github.pagehelper.PageInfo;
import com.xdbigdata.framework.common.model.PageResult;
import com.xdbigdata.framework.utils.FileUploadUtils;
import com.xdbigdata.framework.utils.FreemarkerUtils;
import com.xdbigdata.framework.web.annotation.NeedRole;
import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.constant.CommonConstant;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.mapper.ReportMapper;
import com.xdbigdata.user_manage_admin.model.domain.Report;
import com.xdbigdata.user_manage_admin.model.domain.ReportStatistics;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerInfoDto;
import com.xdbigdata.user_manage_admin.model.qo.ReportQo;
import com.xdbigdata.user_manage_admin.model.qo.student.StudentInfoListQo;
import com.xdbigdata.user_manage_admin.model.vo.CustomSelectionResultVo;
import com.xdbigdata.user_manage_admin.model.vo.StudentInfoVo;
import com.xdbigdata.user_manage_admin.model.vo.StudentReportVo;
import com.xdbigdata.user_manage_admin.model.vo.manager.ManagerInfoVo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentInfoListVo;
import com.xdbigdata.user_manage_admin.service.ReportStatisticsService;
import com.xdbigdata.user_manage_admin.service.StudentManageService;
import com.xdbigdata.user_manage_admin.service.StudentService;
import com.xdbigdata.user_manage_admin.service.TeacherService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.util.FreemakerUtil;
import com.xdbigdata.user_manage_admin.util.ItextPdfUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/managerInfo")
@Api(value = "辅导员、学院、学校用户-个人信息、学生信息控制层", tags = "辅导员、学院、学校用户-个人信息、学生信息")
@NeedRole({UserTypeConstant.INSTRUCTOR, UserTypeConstant.COLLEGE, UserTypeConstant.SCHOOL, UserTypeConstant.CLASS_TEACHER})
public class ManagerInfoController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentManageService studentManageService;
    @Autowired
    private ReportStatisticsService reportStatisticsService;
    @Autowired
    private ReportMapper reportMapper;


    @ApiOperation("根据sn查询姓名")
    @GetMapping("/findNameBySn/{sn}")
    public JsonResponse<String> findNameBySn(@PathVariable("sn") String sn) {
        return JsonResponse.success(teacherService.findNameBySn(sn));
    }

    @ApiOperation("根据sn查看辅导员、学院、学校用户个人信息")
    @GetMapping("/findBySn")
    public JsonResponse<ManagerInfoVo> findBySn() {
        ManagerInfoVo managerInfoVo = teacherService.findInfoBySn(ContextUtil.getSn());
        return JsonResponse.success(managerInfoVo);
    }

    @PreventRepeatSubmit
    @ApiOperation("修改辅导员、学院、学校用户个人信息")
    @PostMapping("/update")
    public JsonResponse<Object> update(@Valid @RequestBody ManagerInfoDto managerInfoDto) {
        teacherService.update(managerInfoDto);
        return JsonResponse.successMessage("修改成功");
    }

    @PostMapping("/getStudentInfoList")
    @ApiOperation("获取学生信息列表")
    public JsonResponse<PageResult<StudentInfoListVo>> getStudentInfoList(@RequestBody StudentInfoListQo studentInfoListQo) {
        PageResult<StudentInfoListVo> data = studentService.getStudentInfoList(studentInfoListQo);
        return JsonResponse.success(data);
    }

    @PostMapping("/getStudentInfoListNew")
    @ApiOperation("获取学生信息列表-新")
    public JsonResponse<CustomSelectionResultVo> getStudentInfoListNew(@RequestBody StudentInfoListQo studentQo) {
        CustomSelectionResultVo data = studentService.getStudentInfoListNew(studentQo);
        return JsonResponse.success(data);
    }

    @GetMapping("/getStudentAllInfo/{sn}")
    @ApiOperation("获取学生综合信息")
    @NeedRole({UserTypeConstant.INSTRUCTOR, UserTypeConstant.COLLEGE, UserTypeConstant.SCHOOL, UserTypeConstant.ADMIN})
    public JsonResponse getStudentAllInfo(@PathVariable("sn") String sn) throws Exception {
        return JsonResponse.success(studentManageService.getStudentAllInfo(sn));
    }

    @GetMapping("/studentAllInfoDownWord/{sn}")
    @ApiOperation("学生综合信息word下载")
    @NeedRole({UserTypeConstant.INSTRUCTOR, UserTypeConstant.COLLEGE, UserTypeConstant.SCHOOL, UserTypeConstant.ADMIN})
    public void studentAllInfoDownWord(@PathVariable("sn") String sn) throws Exception {
        Map map = studentManageService.getStudentAllInfo(sn);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        FreemakerUtil.createWord(requestAttributes.getResponse(), map, "学生综合信息", "dyyl.ftl");
    }

    @GetMapping("/studentAllInfoDownPDF/{sn}")
    @ApiOperation("学生综合信息PDF下载")
    @NeedRole({UserTypeConstant.INSTRUCTOR, UserTypeConstant.COLLEGE, UserTypeConstant.SCHOOL, UserTypeConstant.ADMIN})
    public void studentAllInfoDownPDF(@PathVariable("sn") String sn) throws Exception {
        Map map = studentManageService.getStudentAllInfo(sn);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse resp = requestAttributes.getResponse();

        resp.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("学生综合信息", "UTF-8") + ".pdf");
        resp.setContentType("application/pdf");
        String htmlStr = FreemarkerUtils.build(ManagerInfoController.class, "/static/ftl").setTemplate("backup.ftl").generate(map);
        ItextPdfUtils.export(htmlStr, resp.getOutputStream());

    }

    @PreventRepeatSubmit
    @ApiOperation("导出学生信息列表")
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public JsonResponse export(@RequestBody StudentInfoListQo studentInfoListQo) {
        String data = studentService.exportStudent(studentInfoListQo);
        return JsonResponse.success(data);
    }

    @PreventRepeatSubmit
    @ApiOperation("下载学生照片")
    @RequestMapping(value = "/downloadPhotos/{type}", method = RequestMethod.POST)
    @ApiImplicitParam(name = "type", value = "相片类型(1入学照,2生活照)", dataType = "int", paramType = "path", allowableValues = "1,2", required = true)
    public void downloadPhotos(@RequestBody StudentInfoListQo studentInfoListQo, @PathVariable("type") Integer type,
                               HttpServletResponse response) throws UnsupportedEncodingException {
        if (CollectionUtils.isEmpty(studentInfoListQo.getCollegeCodeList()) ||
                Objects.isNull(studentInfoListQo.getGradeIdList()) ||
                CollectionUtils.isEmpty(studentInfoListQo.getMajorCodeList())) {
            response.addHeader("Error-Message", URLEncoder.encode("照片下载请精确至学院，专业，年级", "UTF-8"));
            return;
        }
        if (Objects.isNull(type) || (type != 1 && type != 2)) {
            response.addHeader("Error-Message", URLEncoder.encode("请选择在正确的照片类型", "UTF-8"));
            return;
        }
        try {
            studentService.downloadPhotos(studentInfoListQo, type, response);
        } catch (Exception e) {
            response.addHeader("Error-Message", URLEncoder.encode("下载照片失败", "UTF-8"));
            return;
        }
    }

    @ApiOperation("根据sn获取学生信息")
    @GetMapping("getStudentBaseInfo/{sn}")
    @ApiImplicitParam(name = "sn", value = "学生sn", dataType = "String", paramType = "path", required = true)
    @NeedRole({UserTypeConstant.INSTRUCTOR, UserTypeConstant.COLLEGE, UserTypeConstant.SCHOOL, UserTypeConstant.ADMIN})
    public JsonResponse<StudentInfoVo> getStudentBaseInfo(@PathVariable("sn") String sn) {
        StudentInfoVo studentInfoVo = studentManageService.getStudentInfo(sn);
        return JsonResponse.success(studentInfoVo);
    }

    @PreventRepeatSubmit
    @ApiOperation("上传学生入学照片")
    @PostMapping(value = "/uploadLifeHead/{sn}")
    @NeedRole(UserTypeConstant.SCHOOL)
    @ApiImplicitParam(name = "sn", value = "学生sn", dataType = "String", paramType = "path", required = true)
    public JsonResponse<String> uploadFile(@RequestParam(value = "file") MultipartFile file, @PathVariable("sn") String sn) {
        FileUploadUtils.verifySuffix(file.getOriginalFilename(), CommonConstant.HEAD_ALLOW_SUFFIXES);
        String data = studentManageService.updateLifeHead(file, sn);
        return JsonResponse.success(data);
    }


    @ApiOperation("筛选学生成绩信息")
    @PostMapping("getReport")
    public JsonResponse<PageInfo<StudentReportVo>> getReportByQuery(@RequestBody ReportQo reportQo) {
        PageInfo<StudentReportVo> list = reportStatisticsService.getReportByQuery(reportQo);
        return JsonResponse.success(list);
    }

    @ApiOperation("根据id查询学生成绩详情信息")
    @GetMapping("getReportItem/{id}")
    public JsonResponse<List<Report>> getReportItem(@PathVariable("id") @ApiParam("成绩统计列表id") Long id) {
        List<Report> reportList = reportStatisticsService.findReportItemById(id);
        return JsonResponse.success(reportList);
    }

    @ApiOperation("获取所有学期")
    @GetMapping("getAllSemester")
    public JsonResponse<List<Integer>> getAllSemester() {
        List<Integer> semesterList = reportMapper.findGroupBySemester();
        return JsonResponse.success(semesterList);
    }

}
