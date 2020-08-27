package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.fastdfs.FastDFSClient;
import com.xdbigdata.framework.utils.FileUploadUtils;
import com.xdbigdata.framework.web.annotation.PreventRepeatSubmit;
import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.framework.web.utils.DownloadUtils;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.constant.CategoryConstant;
import com.xdbigdata.user_manage_admin.constant.CommonConstant;
import com.xdbigdata.user_manage_admin.constant.CustomSelectionConstant;
import com.xdbigdata.user_manage_admin.mapper.*;
import com.xdbigdata.user_manage_admin.model.*;
import com.xdbigdata.user_manage_admin.model.domain.CustomSelection;
import com.xdbigdata.user_manage_admin.model.dto.manager.InstructorManageStuInfDto;
import com.xdbigdata.user_manage_admin.model.vo.CityVO;
import com.xdbigdata.user_manage_admin.model.vo.DictionaryTypeVo;
import com.xdbigdata.user_manage_admin.model.vo.OriginPlaceVo;
import com.xdbigdata.user_manage_admin.model.vo.QuzzyQueryVO;
import com.xdbigdata.user_manage_admin.service.DictionaryService;
import com.xdbigdata.user_manage_admin.service.OrganizationService;
import com.xdbigdata.user_manage_admin.service.StudentService;
import com.xdbigdata.user_manage_admin.util.OriginPlaceUtils;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(value = "公共接口控制层", tags = "公共模块")
@RequestMapping("/api/common")
public class CommonController {

    @Autowired
    private NationMapper nationMapper;

    @Autowired
    private CampusMapper campusMapper;

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private PoliticStatusMapper politicStatusMapper;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private CustomSelectionMapper customSelectionMapper;

    @Autowired
    private StudentService studentService;
    @Autowired
    private DictionaryService dictionaryService;

    @GetMapping("listInstructorManageStuInfDto/{sn}")
    @ApiOperation("获取辅导员管辖范围")
    public JsonResponse<List<InstructorManageStuInfDto>> listInstructorManageStuInfDto(@PathVariable("sn") String sn) {
        return JsonResponse.success(organizationService.listInstructorManageStuInfDto(sn));
    }

    @GetMapping("getColleges")
    @ApiOperation("获取所有的学院")
    public ResultBean<List<OrganizationModel>> getColleges() {
        List<OrganizationModel> datas = organizationService.getAllColleges();
        return new ResultBean<>(datas);
    }

    @GetMapping("getManageColleges")
    @ApiOperation("获取管理的所有学院")
    public ResultBean<List<OrganizationModel>> getManageColleges() {
        List<OrganizationModel> datas = organizationService.getManageColleges();
        return new ResultBean<>(datas);
    }

    @GetMapping("getMajorsByCollege/{collegeId}")
    @ApiOperation("根据学院获取专业")
    @ApiImplicitParam(name = "collegeId", value = "学院id", dataType = "long", paramType = "path", required = true)
    public ResultBean<List<OrganizationModel>> getMajorsByCollege(@PathVariable("collegeId") Long collegeId) {
        List<OrganizationModel> datas = organizationService.getByParentAndType(collegeId, CategoryConstant.CATEGORY_MAJOR);
        return new ResultBean<>(datas);
    }


    @GetMapping("getMajorsByColleges/{collegeCodeList}")
    @ApiOperation("根据学院集合获取专业")
    @ApiImplicitParam(name = "collegeCodeList", value = "学院code集合", dataType = "long", paramType = "path", required = true)
    public ResultBean<List<OrganizationModel>> getMajorsByColleges(@PathVariable("collegeCodeList") List<String> collegeCodeList) {
        List<OrganizationModel> datas = organizationService.getByParentAndTypeList(collegeCodeList, CategoryConstant.CATEGORY_MAJOR);
        return new ResultBean<>(datas);
    }

    @GetMapping("getClassesByMajor/{majorId}")
    @ApiOperation("根据专业获取所有班级")
    @ApiImplicitParam(name = "majorId", value = "专业id", dataType = "long", paramType = "path", required = true)
    public ResultBean<List<OrganizationModel>> getClassesByMajor(@PathVariable("majorId") Long majorId) {
        List<OrganizationModel> datas = organizationService.getByParentAndType(majorId, CategoryConstant.CATEGORY_CLASS);
        return new ResultBean<>(datas);
    }

    @GetMapping("getClassesByMajorList/{majorCodeList}")
    @ApiOperation("根据专业集合获取所有班级")
    @ApiImplicitParam(name = "majorCodeList", value = "专业code集合", dataType = "long", paramType = "path", required = true)
    public ResultBean<List<OrganizationModel>> getClassesByMajorList(@PathVariable("majorCodeList") List<String> majorCodeList) {
        List<OrganizationModel> datas = organizationService.getByParentAndTypeList(majorCodeList, CategoryConstant.CATEGORY_CLASS);
        return new ResultBean<>(datas);
    }

    @GetMapping("getClassesByMajorAndGrade/{majorId}/{grade}")
    @ApiOperation("根据专业获取所有班级")
    @ApiImplicitParam(name = "majorId", value = "专业id", dataType = "long", paramType = "path", required = true)
    public ResultBean<List<OrganizationModel>> getClassesByMajorAndGrade(@PathVariable("majorId") Long majorId, @PathVariable("grade") String grade) {
        List<OrganizationModel> datas = organizationService.getClassesByMajorAndGrade(majorId, grade);
        return new ResultBean<>(datas);
    }

    @GetMapping("getClassesByMajorAndGradeList/{majorCodeList}/{gradeList}")
    @ApiOperation("根据专业集合和年级集合获取所有班级")
    public ResultBean<List<OrganizationModel>> getClassesByMajorAndGradeList(@PathVariable("majorCodeList") @ApiParam("专业code集合") List<String> majorCodeList,
                                                                             @PathVariable("gradeList") @ApiParam("年级集合") List<String> gradeList) {
        List<OrganizationModel> datas = organizationService.getClassesByMajorAndGradeList(majorCodeList, gradeList);
        return new ResultBean<>(datas);
    }

    @GetMapping("getGradesByMajor/{majorId}")
    @ApiOperation("根据专业获取所有年级")
    @ApiImplicitParam(name = "majorId", value = "专业id", dataType = "long", paramType = "path", required = true)
    public ResultBean<Set<String>> getGradesByMajor(@PathVariable("majorId") Long majorId) {
        Set<String> datas = organizationService.getByParentAndType(majorId, CategoryConstant.CATEGORY_CLASS)
                .stream()
                .map(OrganizationModel::getGrade)
                .sorted()
                .collect(Collectors.toSet());
        return new ResultBean<>(datas);
    }

    @GetMapping("getGradesByMajorList/{majorIdList}")
    @ApiOperation("根据专业id集合获取所有年级")
    @ApiImplicitParam(name = "majorIdList", value = "专业id集合", dataType = "long", paramType = "path", required = true)
    public ResultBean<Set<String>> getGradesByMajor(@PathVariable("majorIdList") List<Long> majorIdList){
        Set<String> datas = organizationService.getByParentListAndType(majorIdList, CategoryConstant.CATEGORY_CLASS)
                .stream()
                .map(OrganizationModel::getGrade)
                .sorted()
                .collect(Collectors.toSet());
        return new ResultBean<>(datas);
    }

    @GetMapping("getGradesByCollege/{collegeId}")
    @ApiOperation("根据学院获取所有年级")
    @ApiImplicitParam(name = "collegeId", value = "学院id", dataType = "long", paramType = "path", required = true)
    public ResultBean<List<String>> getGradesByCollege(@PathVariable("collegeId") Long collegeId) {
        List<String> datas = organizationService.getGradesByCollege(collegeId);
        return new ResultBean<>(datas);
    }

    @GetMapping("getAllGrade")
    @ApiOperation("获取所有年级")
    public ResultBean<List<GradeModel>> getAllGrade() {
        List<GradeModel> gradeModelList = gradeMapper.findAllGrade();
        return new ResultBean<>(gradeModelList);
    }

    @GetMapping("getNations")
    @ApiOperation("获取所有的民族")
    public ResultBean<List<NationModel>> getNations() {
        List<NationModel> datas = nationMapper.selectAll();
        return new ResultBean<>(datas);
    }

    @GetMapping("getProvinces")
    @ApiOperation("获取所有的省份")
    public ResultBean<List<ProvinceModel>> getProvinces() {
        List<ProvinceModel> datas = provinceMapper.selectAll();
        return new ResultBean<>(datas);
    }

    @GetMapping("getProvincesAll")
    @ApiOperation("获取所有的行政区(新表)")
    public JsonResponse<List<OriginPlaceVo>> getProvincesAll() {
        List<OriginPlaceVo> data = OriginPlaceUtils.getAllOriginPlace();
        return JsonResponse.success(data);
    }

    @PreventRepeatSubmit
    @PostMapping(value = "/uploadFile")
    @ApiOperation(value = "上传头像", notes = "上传头像接口")
    public JsonResponse<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        // 验证后缀名
        FileUploadUtils.verifySuffix(file.getOriginalFilename(), CommonConstant.HEAD_ALLOW_SUFFIXES);
        String url = FastDFSClient.uploadWithMultipart(file);
        log.error("上传到FastDFS的文件: {}", url);
        return JsonResponse.success(url);
    }

    @GetMapping("getPoliticStatus")
    @ApiOperation("获取所有的政治面貌")
    public ResultBean<List<PoliticStatusModel>> getPoliticStatus() {
        List<PoliticStatusModel> datas = politicStatusMapper.selectAll();
        return new ResultBean<>(datas);
    }

    @GetMapping("getCampus")
    @ApiOperation("获取所有的校区")
    public ResultBean<List<CampusModel>> getCampus() {
        List<CampusModel> datas = campusMapper.selectAll();
        return new ResultBean<>(datas);
    }

    @GetMapping("getHeadPic")
    @ApiOperation(value = "获取头像图片")
    public void getHeadPic(@RequestParam("picUrl") String picUrl, HttpServletResponse response) {
        FastDFSClient.download(picUrl, response);
    }

    @GetMapping("downloadFile")
    @ApiOperation("下载文件")
    @PreventRepeatSubmit
    @ApiImplicitParams({
            @ApiImplicitParam(value = "下载的文件名", name = "name", paramType = "query", dataType = "string", required = true),
            @ApiImplicitParam(value = "文件地址", name = "path", paramType = "query", dataType = "string", required = true)
    })
    public JsonResponse<Object> downloadFile(String name, String path, HttpServletResponse response) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(path)) {
            return JsonResponse.failure("文件名或路径不能为空");
        }
        String url = CommonConstant.FILEPATH + File.separator + path;
        DownloadUtils.download(name, null, url, response);
        return JsonResponse.successMessage("下载成功");
    }

    @ApiOperation("获取全部动态选择展示字段")
    @GetMapping("findAllCustomSelection")
    public JsonResponse findAllCustomSelection() {

        List<CustomSelection> customSelections = customSelectionMapper.queryCustomOrder();
        if (customSelections != null && customSelections.size() > 0) {
            Map<String, List<Map<String, String>>> map = new HashMap();
            List<Map<String, String>> basic = new ArrayList<>();
            List<Map<String, String>> edu = new ArrayList<>();
            List<Map<String, String>> communi = new ArrayList<>();
            List<Map<String, String>> entrance = new ArrayList<>();
            for (CustomSelection customSelection : customSelections) {
                if (CustomSelectionConstant.STUDENT_BASIC.equals(customSelection.getBelongTable())) {
                    Map<String, String> Maps = new HashMap<String, String>();
                    Maps.put(CustomSelectionConstant.FIELD_NAME, customSelection.getName());
                    Maps.put(CustomSelectionConstant.FIELD_CODE, customSelection.getFieldName());
                    basic.add(Maps);
                } else if (CustomSelectionConstant.STUDENT_EDU.equals(customSelection.getBelongTable())) {
                    Map<String, String> Maps = new HashMap<String, String>();
                    Maps.put(CustomSelectionConstant.FIELD_NAME, customSelection.getName());
                    Maps.put(CustomSelectionConstant.FIELD_CODE, customSelection.getFieldName());
                    edu.add(Maps);
                } else if (CustomSelectionConstant.STUDENT_COMMUNI.equals(customSelection.getBelongTable())) {
                    Map<String, String> Maps = new HashMap<String, String>();
                    Maps.put(CustomSelectionConstant.FIELD_NAME, customSelection.getName());
                    Maps.put(CustomSelectionConstant.FIELD_CODE, customSelection.getFieldName());
                    communi.add(Maps);
                } else if (CustomSelectionConstant.STUDENT_ENTRANCE.equals(customSelection.getBelongTable())) {
                    Map<String, String> Maps = new HashMap<String, String>();
                    Maps.put(CustomSelectionConstant.FIELD_NAME, customSelection.getName());
                    Maps.put(CustomSelectionConstant.FIELD_CODE, customSelection.getFieldName());
                    entrance.add(Maps);
                }
            }
            map.put("基本信息", basic);
            map.put("学籍信息", edu);
            map.put("通讯信息", communi);
            map.put("入学信息", entrance);
            return JsonResponse.success(map);
        } else {
            return JsonResponse.success(null);
        }
    }

    @PostMapping("/selectCityByName")
    @ApiOperation("通过站点名称模糊匹配站点信息")
    public JsonResponse selectCityByName(@RequestBody QuzzyQueryVO quzzyQueryVO) {
        List<CityVO> citys = studentService.selectCityByName(quzzyQueryVO);
        return JsonResponse.success(citys);
    }


//    @GetMapping("/getAllCampus")
//    @ApiOperation("获取所有校区")
//    public ResultBean<List<CampusModel>> getAllCampus() {
//        List<CampusModel> campusModels = campusMapper.selectAll();
//        return new ResultBean<>(campusModels);
//    }


}
