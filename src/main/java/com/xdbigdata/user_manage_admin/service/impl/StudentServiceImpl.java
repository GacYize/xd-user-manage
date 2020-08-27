package com.xdbigdata.user_manage_admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.framework.common.model.PageResult;
import com.xdbigdata.framework.excel.exception.ExcelHandlerException;
import com.xdbigdata.framework.excel.handler.POIExcelDownloadHandler;
import com.xdbigdata.framework.excel.handler.POIExcelUploadHandler;
import com.xdbigdata.framework.mybatis.model.MybatisPageResult;
import com.xdbigdata.framework.utils.FileUploadUtils;
import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.annotation.OperateLog;
import com.xdbigdata.user_manage_admin.constant.CommonConstant;
import com.xdbigdata.user_manage_admin.constant.CustomSelectionConstant;
import com.xdbigdata.user_manage_admin.feign.WelcomeFeign;
import com.xdbigdata.user_manage_admin.mapper.*;
import com.xdbigdata.user_manage_admin.model.*;
import com.xdbigdata.user_manage_admin.model.dto.student.ClazzStudentDto;
import com.xdbigdata.user_manage_admin.model.dto.student.UploadStudentDto;
import com.xdbigdata.user_manage_admin.model.qo.XjydQo;
import com.xdbigdata.user_manage_admin.model.qo.student.*;
import com.xdbigdata.user_manage_admin.model.qo.updatecityQO;
import com.xdbigdata.user_manage_admin.model.vo.*;
import com.xdbigdata.user_manage_admin.model.vo.student.ListClazzStudentVo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentInfoListVo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentVo;
import com.xdbigdata.user_manage_admin.model.vo.teacher.InstructorNameVO;
import com.xdbigdata.user_manage_admin.service.StudentService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.util.RemoteFeignUtils;
import com.xdbigdata.user_manage_admin.util.ZipUtils;
import com.xdbigdata.user_manage_admin.util.masterworker.DownloadPhotoTask;
import com.xdbigdata.user_manage_admin.util.masterworker.Master;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private static final String MANAGE_SCOPE = "MANAGE_SCOPE";
    private static final String DISABLEDSTR = "是否残疾";
    private static final String DISABLED = "disabled";
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrganizationLineMapper organizationLineMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private StudentOrganizationLineMapper studentOrganizationLineMapper;

    @Autowired
    private UserManagementMapper userManagementMapper;

    @Autowired
    private WelcomeFeign welcomeFeign;

    @Override
    public ListClazzStudentVo findByClassId(ListClazzStudentQo listClazzStudentQo) {
        PageHelper.startPage(listClazzStudentQo.getPageNum(), listClazzStudentQo.getPageSize());
        Page<ClazzStudentDto> page = (Page<ClazzStudentDto>) studentMapper.selectStudentsByClazz(listClazzStudentQo);

        return new ListClazzStudentVo(page, page.getResult());
    }

    @Override
    public StudentVo findById(Long id) {
        //获取基本信息
        StudentVo studentVo = studentMapper.findByUserId(id);
        //获取所属组织机构
        List<OrganizationModel> organizationModelList = organizationMapper.findByUserId(id);
        if (organizationModelList != null && organizationModelList.size() > 0) {
            for (OrganizationModel organizationModel : organizationModelList) {
                if (Objects.equals(organizationModel.getType(), 1)) {
                    studentVo.setCollegeName(organizationModel.getName());
                } else if (Objects.equals(organizationModel.getType(), 2)) {
                    studentVo.setMajorName(organizationModel.getName());
                } else if (Objects.equals(organizationModel.getType(), 4)) {
                    studentVo.setClassName(organizationModel.getName());
                    studentVo.setGrade(organizationModel.getGrade());
                }
            }
        }
        return studentVo;
    }

    @Override
    @Transactional
    @OperateLog(module = "人员管理")
    public void addOrUpdate(AddAndUpdateStudentQo addAndUpdateStudentQo) {
        UserModel newUserModel = new UserModel();
        StudentModel newStudentModel = new StudentModel();
        StudentOrganizationLineModel newStudentOrganizationLineModel = new StudentOrganizationLineModel();
        UserRoleModel newUserRoleModel = new UserRoleModel();
        if (addAndUpdateStudentQo.getId() == null) {
            //验证sn不能重复
            UserModel userModel = userMapper.selectUserBySn(addAndUpdateStudentQo.getSn());
            if (userModel != null) {
                throw new BaseException("学号重复，不能添加");
            }
            BeanUtils.copyProperties(addAndUpdateStudentQo, newUserModel);
            newUserModel.setValid(1);
            //添加到表t_user表中
            userMapper.insertSelective(newUserModel);
            //添加到t_user_role表中
            RoleModel roleModel = roleMapper.findByName(CommonConstant.STUDENT_ROLE);
            if (roleModel == null) {
                throw new BaseException("当前缺少学生用户角色，新增失败");
            }
            newUserRoleModel.setJoinTime(new Date());
            newUserRoleModel.setUserId(newUserModel.getId());
            newUserRoleModel.setRoleId(roleModel.getId());
            newUserRoleModel.setValid(1);
            userRoleMapper.insertSelective(newUserRoleModel);
            //添加到t_student_organization_line表中
            Long lineId = organizationLineMapper.selectLineIdByClazz(addAndUpdateStudentQo.getClassId());
            newStudentOrganizationLineModel.setLineId(lineId);
            newStudentOrganizationLineModel.setStudentId(newUserModel.getId());
            studentOrganizationLineMapper.insertStudentOrganizationLine(newStudentOrganizationLineModel);
            //添加到t_student表中
            BeanUtils.copyProperties(addAndUpdateStudentQo, newStudentModel);
            newStudentModel.setValid(1);
            OrganizationModel organizationModel = organizationMapper.findById(addAndUpdateStudentQo.getClassId());
            newStudentModel.setUserId(newUserModel.getId());
            newStudentModel.setGrade(organizationModel.getGrade());
            newStudentModel.setStatus(0);
            studentMapper.insertSelective(newStudentModel);
            ContextUtil.LOG_THREADLOCAL.set("新增学生" + addAndUpdateStudentQo.getName());
        } else {
            BeanUtils.copyProperties(addAndUpdateStudentQo, newUserModel);
            newUserModel.setValid(1);
            //修改表t_user
            userMapper.updateByPrimaryKey(newUserModel);
            //修改表t_student
            BeanUtils.copyProperties(addAndUpdateStudentQo, newStudentModel);
            newStudentModel.setUserId(newUserModel.getId());
            StudentModel studentModel = studentMapper.selectByUserId(addAndUpdateStudentQo.getId());
            newStudentModel.setId(studentModel.getId());
            newStudentModel.setValid(1);
            newStudentModel.setStatus(0);
            newStudentModel.setGrade(studentModel.getGrade());
            studentMapper.updateByPrimaryKeySelective(newStudentModel);
            ContextUtil.LOG_THREADLOCAL.set("修改学生" + addAndUpdateStudentQo.getName());
        }
    }

    @Override
    @Transactional
    @OperateLog(module = "人员管理")
    public List<String> uploadStudentExcel(MultipartFile file, Long classId) throws Exception {
        List<String> problemSns = new ArrayList<>();
        List<UploadStudentDto> uploadStudentDtoList = null;
        try {
            uploadStudentDtoList = POIExcelUploadHandler.excel2Entities(file.getInputStream(), 1, UploadStudentDto.class);
        } catch (ExcelHandlerException e) {
            throw new BaseException("请按照模板输入完整正确的学工号和姓名");
        } catch (Exception e) {
            throw new BaseException("请使用正确的Excel文件");
        }
        if (uploadStudentDtoList != null && uploadStudentDtoList.size() > 0) {
            //验证上传Excel中sns和数据库中sn是否重复
            List<String> studentSns = new ArrayList<>();
            for (UploadStudentDto uploadStudentDto : uploadStudentDtoList) {
                studentSns.add(uploadStudentDto.getSn());
            }
            List<UserModel> userModelList = userMapper.findIdAndSnBySnS(studentSns, null);
            if (userModelList != null && userModelList.size() > 0) {
                for (UserModel userModel : userModelList) {
                    problemSns.add(userModel.getSn());
                }
            }
            //验证Excel中sn自身是否重复
            uploadStudentDtoList.stream().collect(Collectors.groupingBy(UploadStudentDto::getSn)).forEach((sn, studentList) -> {
                if (studentList.size() > 1) {
                    problemSns.add(sn);
                }
            });
            if (problemSns.size() == 0) {
                //添加到t_user表中
                List<UserModel> userModelList1 = new ArrayList<>();
                for (UploadStudentDto uploadStudentDto : uploadStudentDtoList) {
                    UserModel userModel = new UserModel();
                    BeanUtils.copyProperties(uploadStudentDto, userModel);
                    userModel.setValid(1);
                    userModelList1.add(userModel);
                }
                userMapper.addUserList(userModelList1);
                List<UserModel> userModelList2 = userMapper.findIdAndSnBySnS(studentSns, null);
                //添加到t_user_role表中
                RoleModel roleModel = roleMapper.findByName(CommonConstant.STUDENT_ROLE);
                if (roleModel == null) {
                    throw new BaseException("当前缺少学生用户角色，新增失败");
                }
                List<UserRoleModel> userRoleModelList = new ArrayList<>();
                List<StudentModel> studentModelList = new ArrayList<>();
                List<StudentOrganizationLineModel> studentOrganizationLineList = new ArrayList<>();
                Date joinTime = new Date();
                Long lineId = organizationLineMapper.selectLineIdByClazz(classId);
                OrganizationModel organizationModel = organizationMapper.findById(classId);
                for (UserModel userModel : userModelList2) {
                    //添加userRoleModel
                    UserRoleModel userRoleModel = new UserRoleModel();
                    userRoleModel.setValid(1);
                    userRoleModel.setJoinTime(joinTime);
                    userRoleModel.setRoleId(roleModel.getId());
                    userRoleModel.setUserId(userModel.getId());
                    userRoleModelList.add(userRoleModel);
                    //添加studentOrganizationLineModel
                    StudentOrganizationLineModel studentOrganizationLineModel = new StudentOrganizationLineModel();
                    studentOrganizationLineModel.setLineId(lineId);
                    studentOrganizationLineModel.setStudentId(userModel.getId());
                    studentOrganizationLineList.add(studentOrganizationLineModel);
                    //添加studentModel
                    StudentModel studentModel = new StudentModel();
                    studentModel.setValid(1);
                    studentModel.setUserId(userModel.getId());
                    studentModel.setStatus(0);
                    studentModel.setGrade(organizationModel.getGrade());
                    studentModel.setSn(userModel.getSn());
                    studentModelList.add(studentModel);
                }
                userRoleMapper.insertList(userRoleModelList);
                studentOrganizationLineMapper.insertList(studentOrganizationLineList);
                studentMapper.insertList(studentModelList);
                ContextUtil.LOG_THREADLOCAL.set("新增学生“" + uploadStudentDtoList.get(0).getName() + "”等" + uploadStudentDtoList.size() + "个学生");
            }
        } else {
            throw new BaseException("当前上传的Excel无数据");
        }
        return problemSns;
    }

    @Override
    @OperateLog(module = "人员管理")
    @Transactional
    public String moveClazzStudent(MoveClazzStudentQo moveClazzStudentQo) {
        Long clazzId = moveClazzStudentQo.getClazzId();
        Long studentId = moveClazzStudentQo.getStudentId();
        Long dbLineId = organizationLineMapper.selectLineIdByClazz(clazzId);
        UserModel student = userMapper.selectByPrimaryKey(studentId);
        if (student == null) {
            throw new BaseException("学生不存在，移动失败");
        }
        OrganizationModel clazz = organizationMapper.selectByPrimaryKey(clazzId);
        if (clazz == null) {
            throw new BaseException("班级不存在，移动失败");
        }
        StudentOrganizationLineModel studentOrganizationLineModel = new StudentOrganizationLineModel(studentId);
        if (dbLineId == null) {
            Long maxLineId = organizationLineMapper.getMaxLineId();
            Long nextLineId = maxLineId == null ? 0 : maxLineId + 1;
            List<OrganizationLineModel> organizationLineModelList = Arrays.asList(
                    new OrganizationLineModel(1L, nextLineId),
                    new OrganizationLineModel(moveClazzStudentQo.getCollegeId(), nextLineId),
                    new OrganizationLineModel(moveClazzStudentQo.getMajorId(), nextLineId),
                    new OrganizationLineModel(clazzId, nextLineId)
            );

            organizationLineMapper.insertOrganizationLineList(organizationLineModelList);
            studentOrganizationLineModel.setLineId(nextLineId);
        } else {
            studentOrganizationLineModel.setLineId(dbLineId);
        }
        studentMapper.updateStudentGrade(studentId, clazz.getGrade());
        studentOrganizationLineMapper.updateLine(studentOrganizationLineModel);
        ContextUtil.LOG_THREADLOCAL.set("移动学生:" + student.getName() + "到班级:" + clazz.getName());
        return "移动成功";
    }

    @Override
    @OperateLog(module = "人员管理")
    @Transactional
    public String removeClazzStudent(Long studentId) {
        UserModel userModel = userMapper.selectByPrimaryKey(studentId);
        if (userModel == null) {
            throw new BaseException("用户不存在，删除失败");
        }
        if (userModel.getValid() == -1) {
            throw new BaseException("该用户已被删除，请勿重复操作");
        }

        // -1标记为删除
        userModel.setValid(-1);

        userMapper.updateByPrimaryKeySelective(userModel);
        StudentOrganizationLineModel studentOrganizationLineModel = new StudentOrganizationLineModel();
        studentOrganizationLineModel.setStudentId(studentId);
        studentOrganizationLineMapper.delete(studentOrganizationLineModel);
        ContextUtil.LOG_THREADLOCAL.set("删除用户:" + userModel.getName());

        return "删除成功";
    }

    @Override
    public UserModel findBySnAndOnlyStudentRole(String sn) {
        UserModel userModel = userMapper.findBySnAndRole(sn, CommonConstant.STUDENT_ROLE);
        if (userModel != null) {
            List<RoleModel> roleModelList = roleMapper.findByUserId(userModel.getId());
            if (roleModelList != null && roleModelList.size() == 1) {
                return userModel;
            }
        }
        return null;
    }

    @Override
    public PageResult<StudentInfoListVo> getStudentInfoList(StudentInfoListQo studentInfoListQo) {
        setManageScope(studentInfoListQo);
        PageHelper.startPage(studentInfoListQo.getPgCt(), studentInfoListQo.getPgSz());
        Page<StudentInfoListVo> studentInfoListVos = (Page<StudentInfoListVo>) studentMapper.selectByQuery(studentInfoListQo);
        PageHelper.clearPage();
        if (CollectionUtils.isEmpty(studentInfoListVos)) {
            return new PageResult<>(studentInfoListQo.getPgCt(), studentInfoListQo.getPgSz());
        }
        return new MybatisPageResult<>(studentInfoListVos);
    }

    @Override
    public CustomSelectionResultVo getStudentInfoListNew(StudentInfoListQo studentInfoListQo) {

        setManageScope(studentInfoListQo);
        PageHelper.startPage(studentInfoListQo.getPgCt(), studentInfoListQo.getPgSz());
        List<String> displayField = studentInfoListQo.getDisplayField();

        if (CollectionUtils.isEmpty(displayField)) {
            displayField = new ArrayList<>(); // 增加默认字段
            displayField.add(CustomSelectionConstant.SN);
            displayField.add(CustomSelectionConstant.NAME);
            displayField.add(CustomSelectionConstant.GRADE);
            displayField.add(CustomSelectionConstant.MAJOR);
            displayField.add(CustomSelectionConstant.CLASSES);
            displayField.add(CustomSelectionConstant.NATION_ID);
            displayField.add(CustomSelectionConstant.POLITICS_STATUS_ID);
            displayField.add(CustomSelectionConstant.GENDER_ID);
            displayField.add(CustomSelectionConstant.CURRENT_DOMICILE_PLACE_CODE);
            displayField.add(CustomSelectionConstant.PHONE);
            displayField.add(CustomSelectionConstant.INSTRUCTOR);
        }
        // 组装动态查询
        StringBuilder builder = new StringBuilder();
        strAppend(displayField, builder);
        String display = builder.toString();


        Page<CustomSelectionAllVo> customSelectionAllVos = (Page<CustomSelectionAllVo>) studentMapper.selectByQueryNew(studentInfoListQo, display);
        PageHelper.clearPage();

        CustomSelectionResultVo customSelectionResultVo = new CustomSelectionResultVo();

        if (CollectionUtils.isEmpty(customSelectionAllVos)) {
            customSelectionResultVo.setPageSize(studentInfoListQo.getPgCt());
            customSelectionResultVo.setPageNum(studentInfoListQo.getPgSz());
        } else {
            // 查询数据
            List<CustomSelectionAllVo> customSelectionAllVoList = customSelectionAllVos.getResult();
            List<List<DataResultVo>> resultVo = new ArrayList<>();
            for (CustomSelectionAllVo customSelectionAllVo : customSelectionAllVoList) {
                String sn = customSelectionAllVo.getSn();
                customSelectionAllVo.setRoomno(RemoteFeignUtils.getStudentDorm(sn));
                List<DataResultVo> dataResultVoList = new ArrayList<>();
                displayField.forEach(str -> {
                    DataResultVo dataResultVo = confirmationField(str, customSelectionAllVo);
                    dataResultVoList.add(dataResultVo);
                });
                if (displayField.contains(DISABLED)) {
                    JsonResponse disabledInfBySn;
                    try {
                        disabledInfBySn = welcomeFeign.getDisabledInfBySn(JSONObject.toJSONString(new String[]{sn}));
                        Map data = (HashMap) disabledInfBySn.getData();
                        dataResultVoList.add(new DataResultVo(DISABLEDSTR, (String) data.get(sn), DISABLED));
                    } catch (Exception ex) {
                        log.error("调用迎新接口失败");
                    }
                }
                resultVo.add(dataResultVoList);
            }
            customSelectionResultVo.setPageNum(customSelectionAllVos.getPageNum());
            customSelectionResultVo.setPageSize(customSelectionAllVos.getPageSize());
            customSelectionResultVo.setPages(customSelectionAllVos.getPages());
            customSelectionResultVo.setTotal(customSelectionAllVos.getTotal());
            customSelectionResultVo.setData(resultVo);
        }
        return customSelectionResultVo;
    }

    @Override
    public String export(StudentInfoListQo studentInfoListQo) {
        setManageScope(studentInfoListQo);
//        List<StudentInfoExportVo> studentInfoListVos = studentMapper.exportByQuery(studentInfoListQo);
//        if (CollectionUtils.isEmpty(studentInfoListVos)) {
//            throw new BaseException("无申请数据可导出");
//        }
        studentInfoListQo.setAllDisplayField();
        List<String> displayField = studentInfoListQo.getDisplayField();
        // 组装动态查询
        StringBuilder builder = new StringBuilder();
        strAppend(displayField, builder);
        List<CustomSelectionAllVo> listVo = studentMapper.selectByQueryNew(studentInfoListQo, builder.toString());
        for (CustomSelectionAllVo vo : listVo) {
            String sn = vo.getSn();
            vo.setRoomno(RemoteFeignUtils.getStudentDorm(sn));
            vo.setClass_teacher(RemoteFeignUtils.getClassTeacher(sn));
            vo.setRoomplace(RemoteFeignUtils.getDormitoryArea(sn));
            vo.executeAllGets();
        }

        ByteArrayOutputStream outputStream = POIExcelDownloadHandler.entities2Excel(listVo);
        InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
        return FileUploadUtils.saveFile(CommonConstant.FILEPATH, "EXPORT", "xlsx", is);
    }

    private void strAppend(List<String> displayField, StringBuilder builder) {
        for (int i = 0; i < displayField.size(); i++) {
            if (i == 0) {
                builder.append(displayField.get(i));
            } else {
                builder.append(",").append(displayField.get(i));
            }
        }
    }

    @Override
    public String exportStudent(StudentInfoListQo studentInfoListQo) {
        setManageScope(studentInfoListQo);
        if (studentInfoListQo.getCollegeCodeList() == null || studentInfoListQo.getCollegeCodeList().equals("")) {
            throw new BaseException("学院不能为空");
        }
        List<CustomSelectionAllDownVo> listVo = studentMapper.selectByQueryNewExcel(studentInfoListQo);
        List<AdjustmentRecordDto> adjustmentRecordDtoList = RemoteFeignUtils.getDormitoryAreaAndAdjust();
        Map<String, AdjustmentRecordDto> collect = null;
        if (!CollectionUtils.isEmpty(adjustmentRecordDtoList)) {
            collect = adjustmentRecordDtoList.stream().collect(Collectors.toMap(AdjustmentRecordDto::getSn, Function.identity(), (o1, o2) -> o2));
        }

        for (CustomSelectionAllDownVo vo : listVo) {
            String sn = vo.getSn();
            if (!MapUtils.isEmpty(collect)) {
                AdjustmentRecordDto adjustmentRecordDto = collect.get(sn);
                if (adjustmentRecordDto != null) {
                    vo.setRoomno(adjustmentRecordDto.getAfterPlace());
                    vo.setRoomplace(adjustmentRecordDto.getName());
                    vo.setClass_teacher(vo.getInstructor());
                    if (vo.getS_absentee() != null && vo.getAbsentee() == null && vo.getS_absentee() == true) {
                        vo.setAbsentee("是");
                    } else {
                        vo.setAbsentee("否");
                    }
                    if (vo.getS_at_school() != null && vo.getAt_school() == null && vo.getS_at_school() == true) {
                        vo.setAt_school("是");
                    } else {
                        vo.setAt_school("否");
                    }
                }
            }
        }
        ByteArrayOutputStream outputStream = POIExcelDownloadHandler.entities2Excel(listVo);
        InputStream is = new ByteArrayInputStream(outputStream.toByteArray());
        return FileUploadUtils.saveFile(CommonConstant.FILEPATH, "EXPORT", "xls", is);

    }


    @Override
    public void downloadPhotos(StudentInfoListQo studentInfoListQo, Integer type, HttpServletResponse response) throws Exception {
        setManageScope(studentInfoListQo);
        List<StudentPhoto> studentPhotos = studentMapper.selectPhoto(studentInfoListQo);
        if (CollectionUtils.isEmpty(studentPhotos)) {
            response.addHeader("Error-Message", URLEncoder.encode("当前筛选条件下无学生信息", "UTF-8"));
            return;
        }

        String dirPath = "PHOTO-" + System.currentTimeMillis();
        File dir = new File(".", dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 照片类型1入学照,2生活照
        List<StudentPhoto> photos = studentPhotos.stream().map(sp -> {
            String url = Objects.equals(type, 1) ? sp.getPicUrl() : sp.getLifeHeadUrl();
            if (StringUtils.isBlank(url)) {
                log.warn("学号为:{}的学生无相关照片", sp.getSn());
            } else {
                sp.setFilePath(url);
            }
            return sp;
        }).filter(sp -> StringUtils.isNotBlank(sp.getFilePath()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(photos)) {
            response.addHeader("Error-Message", URLEncoder.encode("当前筛选条件下无学生照片信息", "UTF-8"));
            return;
        }

        Master<Boolean> master = new Master<>();
        photos.forEach(sp -> {
            DownloadPhotoTask downloadPhotoTask = new DownloadPhotoTask(sp, dir);
            master.submit(downloadPhotoTask);
        });
        master.execute();

        while (true) {
            if (master.isComplete()) {
                String downloadName = URLEncoder.encode("学生照片.zip", "UTF-8");
                response.setHeader("Content-disposition", "attachment;filename=" + downloadName);
                ZipUtils.packToolFiles(dir.getPath(), "学生照片", response.getOutputStream());
                ZipUtils.deleteDir(dir.getPath());
                return;
            }
            TimeUnit.SECONDS.sleep(5);
        }
    }

    /**
     * 设置学生列表查询信息中的管辖范围
     *
     * @param studentInfoListQo 学生信息列表查询对象
     */
    private void setManageScope(StudentInfoListQo studentInfoListQo) {
        List<ManageScope> manageScopes = userManagementMapper.selectManageScope(ContextUtil.getId(), ContextUtil.getRoleId());
        if (CollectionUtils.isEmpty(manageScopes)) {
            throw new BaseException("当前登录用户角色未配置管辖范围");
        }
        studentInfoListQo.setManageScopes(manageScopes);
    }


    /**
     * 验证动态字段
     */

    private DataResultVo confirmationField(String str, CustomSelectionAllVo customSelectionAllVo) {
        DataResultVo dataResultVo = new DataResultVo();
        if (CustomSelectionConstant.SN.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.SN_NAME);
            dataResultVo.setValue(customSelectionAllVo.getSn());
            dataResultVo.setCode(CustomSelectionConstant.SN);
        } else if (CustomSelectionConstant.NAME.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.NAME_NAME);
            dataResultVo.setValue(customSelectionAllVo.getName());
            dataResultVo.setCode(CustomSelectionConstant.NAME);
        } else if (CustomSelectionConstant.GRADE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.GRADE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getGrade());
            dataResultVo.setCode(CustomSelectionConstant.GRADE);
        } else if (CustomSelectionConstant.FORMERNAME.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.FORMERNAME_NAME);
            dataResultVo.setValue(customSelectionAllVo.getFormername());
            dataResultVo.setCode(CustomSelectionConstant.FORMERNAME);
        } else if (CustomSelectionConstant.GENDER_ID.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.GENDER_ID_NAME);
            dataResultVo.setValue(customSelectionAllVo.getGender());
            dataResultVo.setCode(CustomSelectionConstant.GENDER_ID);
        } else if (CustomSelectionConstant.NATION_ID.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.NATION_ID_NAME);
            dataResultVo.setValue(customSelectionAllVo.getNation());
            dataResultVo.setCode(CustomSelectionConstant.NATION_ID);
        } else if (CustomSelectionConstant.POLITICS_STATUS_ID.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.POLITICS_STATUS_ID_NAME);
            dataResultVo.setValue(customSelectionAllVo.getPoliticsStatus());
            dataResultVo.setCode(CustomSelectionConstant.POLITICS_STATUS_ID);
        } else if (CustomSelectionConstant.NATIVE_PLACE_CODE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.NATIVE_PLACE_CODE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getNativePlace());
            dataResultVo.setCode(CustomSelectionConstant.NATIVE_PLACE_CODE);
        } else if (CustomSelectionConstant.ID_NUMBER.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.ID_NUMBER_NAME);
            dataResultVo.setValue(customSelectionAllVo.getId_number());
            dataResultVo.setCode(CustomSelectionConstant.ID_NUMBER);
        } else if (CustomSelectionConstant.BIRTH_DATE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.BIRTH_DATE_NAME);
            Date birth = customSelectionAllVo.getBirth_date();
            if (birth != null) {
                DateFormat bf = new SimpleDateFormat("yyyy-MM-dd");//多态
                dataResultVo.setValue(bf.format(birth));
            } else {
                dataResultVo.setValue(null);
            }
            dataResultVo.setCode(CustomSelectionConstant.BIRTH_DATE);
        } else if (CustomSelectionConstant.HEALTH_STATE_ID.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.HEALTH_STATE_ID_NAME);
            dataResultVo.setValue(customSelectionAllVo.getHealthState());
            dataResultVo.setCode(CustomSelectionConstant.HEALTH_STATE_ID);
        } else if (CustomSelectionConstant.BLOOD_TYPE_ID.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.BLOOD_TYPE_ID_NAME);
            dataResultVo.setValue(customSelectionAllVo.getBloodType());
            dataResultVo.setCode(CustomSelectionConstant.BLOOD_TYPE_ID);
        } else if (CustomSelectionConstant.RELIGION_ID.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.RELIGION_ID_NAME);
            dataResultVo.setValue(customSelectionAllVo.getReligion());
            dataResultVo.setCode(CustomSelectionConstant.RELIGION_ID);
        } else if (CustomSelectionConstant.CURRENT_DOMICILE_PLACE_CODE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.CURRENT_DOMICILE_PLACE_CODE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getCurrentDomicilePalce());
            dataResultVo.setCode(CustomSelectionConstant.CURRENT_DOMICILE_PLACE_CODE);
        } else if (CustomSelectionConstant.DEPARTURE_DESTINATION.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.DEPARTURE_DESTINATION_NAME);
            dataResultVo.setValue(customSelectionAllVo.getDeparture_destination());
            dataResultVo.setCode(CustomSelectionConstant.DEPARTURE_DESTINATION);
        } else if (CustomSelectionConstant.MARITAL_STATUS_ID.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.MARITAL_STATUS_ID_NAME);
            dataResultVo.setValue(customSelectionAllVo.getMaritalStatus());
            dataResultVo.setCode(CustomSelectionConstant.MARITAL_STATUS_ID);
        } else if (CustomSelectionConstant.OPENING_BANK.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.OPENING_BANK_NAME);
            dataResultVo.setValue(customSelectionAllVo.getOpening_bank());
            dataResultVo.setCode(CustomSelectionConstant.OPENING_BANK);
        } else if (CustomSelectionConstant.BANK_CARD.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.BANK_CARD_NAME);
            dataResultVo.setValue(customSelectionAllVo.getBank_card());
            dataResultVo.setCode(CustomSelectionConstant.BANK_CARD);
        } else if (CustomSelectionConstant.HOBBY.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.HOBBY_NAME);
            dataResultVo.setValue(customSelectionAllVo.getHobby());
            dataResultVo.setCode(CustomSelectionConstant.HOBBY);
        } else if (CustomSelectionConstant.METRO_CARD.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.METRO_CARD_NAME);
            dataResultVo.setValue(customSelectionAllVo.getMetro_card());
            dataResultVo.setCode(CustomSelectionConstant.METRO_CARD);
        } else if (CustomSelectionConstant.STATURE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.STATURE_NAME);
            if (customSelectionAllVo.getStature() != null) {
                dataResultVo.setValue(customSelectionAllVo.getStature().toString());
            } else {
                dataResultVo.setValue(null);
            }
            dataResultVo.setCode(CustomSelectionConstant.STATURE);
        } else if (CustomSelectionConstant.WEIGHT.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.WEIGHT_NAME);
            if (customSelectionAllVo.getWeight() != null) {
                dataResultVo.setValue(customSelectionAllVo.getWeight().toString());
            } else {
                dataResultVo.setValue(null);
            }
            dataResultVo.setCode(CustomSelectionConstant.WEIGHT);
        } else if (CustomSelectionConstant.PHONE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.PHONE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getPhone());
            dataResultVo.setCode(CustomSelectionConstant.PHONE);
        } else if (CustomSelectionConstant.STANDBY_PHONE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.STANDBY_PHONE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getStandby_phone());
            dataResultVo.setCode(CustomSelectionConstant.STANDBY_PHONE);
        } else if (CustomSelectionConstant.WECHAT.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.WECHAT_NAME);
            dataResultVo.setValue(customSelectionAllVo.getWechat());
            dataResultVo.setCode(CustomSelectionConstant.WECHAT);
        } else if (CustomSelectionConstant.QQ.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.QQ_NAME);
            dataResultVo.setValue(customSelectionAllVo.getQq());
            dataResultVo.setCode(CustomSelectionConstant.QQ);
        } else if (CustomSelectionConstant.EMAIL.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.EMAIL_NAME);
            dataResultVo.setValue(customSelectionAllVo.getEmail());
            dataResultVo.setCode(CustomSelectionConstant.EMAIL);
        } else if (CustomSelectionConstant.FAMILY_PHONE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.FAMILY_PHONE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getFamily_phone());
            dataResultVo.setCode(CustomSelectionConstant.FAMILY_PHONE);
        } else if (CustomSelectionConstant.FAMILY_EMAIL.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.FAMILY_EMAIL_NAME);
            dataResultVo.setValue(customSelectionAllVo.getFamily_email());
            dataResultVo.setCode(CustomSelectionConstant.FAMILY_EMAIL);
        } else if (CustomSelectionConstant.FAMILY_PLACE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.FAMILY_PLACE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getFamilyPlace());
            dataResultVo.setCode(CustomSelectionConstant.FAMILY_PLACE);
        } else if (CustomSelectionConstant.FAMILY_DETAIL_ADDRESS.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.FAMILY_DETAIL_ADDRESS_NAME);
            dataResultVo.setValue(customSelectionAllVo.getFamily_detail_address());
            dataResultVo.setCode(CustomSelectionConstant.FAMILY_DETAIL_ADDRESS);
        } else if (CustomSelectionConstant.ROOMPLACE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.ROOMPLACE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getRoomplace());
            dataResultVo.setCode(CustomSelectionConstant.ROOMPLACE);
        } else if (CustomSelectionConstant.ROOMNO.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.ROOMNO_NAME);
            dataResultVo.setValue(customSelectionAllVo.getRoomno());
            dataResultVo.setCode(CustomSelectionConstant.ROOMNO);
        } else if (CustomSelectionConstant.COLLEGE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.COLLEGE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getCollegeName());
            dataResultVo.setCode(CustomSelectionConstant.COLLEGE);
        } else if (CustomSelectionConstant.MAJOR.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.MAJOR_NAME);
            dataResultVo.setValue(customSelectionAllVo.getMajorName());
            dataResultVo.setCode(CustomSelectionConstant.MAJOR);
        } else if (CustomSelectionConstant.CLASSES.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.CLASSES_NAME);
            dataResultVo.setValue(customSelectionAllVo.getClassName());
            dataResultVo.setCode(CustomSelectionConstant.CLASSES);
        } else if (CustomSelectionConstant.CLASS_TEACHER.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.CLASS_TEACHER_NAME);
            dataResultVo.setValue(customSelectionAllVo.getClass_teacher());
            dataResultVo.setCode(CustomSelectionConstant.CLASS_TEACHER);
        } else if (CustomSelectionConstant.INSTRUCTOR.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.INSTRUCTOR_NAME);
            //dataResultVo.setValue(customSelectionAllVo.getInstructor());
            List<InstructorNameVO> instructorNameVOS = userMapper.queryInstructorByCode(customSelectionAllVo.getClasses());
            setInstructorName(dataResultVo, instructorNameVOS);
            dataResultVo.setCode(CustomSelectionConstant.INSTRUCTOR);
        } else if (CustomSelectionConstant.INSTRUCTOR_PHONE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.INSTRUCTOR_PHONE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getInstructor_phone());
            dataResultVo.setCode(CustomSelectionConstant.INSTRUCTOR_PHONE);
        } else if (CustomSelectionConstant.GRADUATE_DATE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.GRADUATE_DATE_NAME);
            Date graduateDate = customSelectionAllVo.getGraduate_date();
            if (graduateDate != null) {
                DateFormat bf = new SimpleDateFormat("yyyy-MM");//多态
                dataResultVo.setValue(bf.format(graduateDate));
            } else {
                dataResultVo.setValue(null);
            }
            dataResultVo.setCode(CustomSelectionConstant.GRADUATE_DATE);
        } else if (CustomSelectionConstant.SOURCE_TYPE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.SOURCE_TYPE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getSource_type());
            dataResultVo.setCode(CustomSelectionConstant.SOURCE_TYPE);
        } else if (CustomSelectionConstant.EDUCATION_STATUS_ID.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.EDUCATION_STATUS_ID_NAME);
            dataResultVo.setValue(customSelectionAllVo.getEducationStatus());
            dataResultVo.setCode(CustomSelectionConstant.EDUCATION_STATUS_ID);
        } else if (CustomSelectionConstant.EDUCATION_ID.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.EDUCATION_ID_NAME);
            dataResultVo.setValue(customSelectionAllVo.getEducation());
            dataResultVo.setCode(CustomSelectionConstant.EDUCATION_ID);
        } else if (CustomSelectionConstant.ABSENTEE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.ABSENTEE_NAME);
            if (customSelectionAllVo.getAbsentee() != null) {
                if (customSelectionAllVo.getAbsentee()) {
                    dataResultVo.setValue("是");
                } else {
                    dataResultVo.setValue("否");
                }
            } else {
                dataResultVo.setValue(null);
            }
            dataResultVo.setCode(CustomSelectionConstant.ABSENTEE);
        } else if (CustomSelectionConstant.AT_SCHOOL.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.AT_SCHOOL_NAME);
            if (customSelectionAllVo.getAt_school() != null) {
                if (customSelectionAllVo.getAt_school()) {
                    dataResultVo.setValue("是");
                } else {
                    dataResultVo.setValue("否");
                }
            } else {
                dataResultVo.setValue(null);
            }
            dataResultVo.setCode(CustomSelectionConstant.AT_SCHOOL);
        } else if (CustomSelectionConstant.ENTER_DATE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.ENTER_DATE_NAME);
            if (customSelectionAllVo.getEnter_date() != null) {
                DateFormat bf = new SimpleDateFormat("yyyy-MM-dd");
                dataResultVo.setValue(bf.format(customSelectionAllVo.getEnter_date()));
            } else {
                dataResultVo.setValue(null);
            }
            dataResultVo.setCode(CustomSelectionConstant.ENTER_DATE);
        } else if (CustomSelectionConstant.DOMICILE_PLACE_CODE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.DOMICILE_PLACE_CODE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getDomicilePlace());
            dataResultVo.setCode(CustomSelectionConstant.DOMICILE_PLACE_CODE);
        } else if (CustomSelectionConstant.SOURCE_PLACE_CODE.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.SOURCE_PLACE_CODE_NAME);
            dataResultVo.setValue(customSelectionAllVo.getSourcePlace());
            dataResultVo.setCode(CustomSelectionConstant.SOURCE_PLACE_CODE);
        } else if (CustomSelectionConstant.HIGH_SCHOOL.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.HIGH_SCHOOL_NAME);
            dataResultVo.setValue(customSelectionAllVo.getHigh_school());
            dataResultVo.setCode(CustomSelectionConstant.HIGH_SCHOOL);
        } else if (CustomSelectionConstant.SAT_SCORES.equals(str)) {
            dataResultVo.setName(CustomSelectionConstant.SAT_SCORES_NAME);
            if (customSelectionAllVo.getSat_scores() != null) {
                dataResultVo.setValue(customSelectionAllVo.getSat_scores().toString());
            } else {
                dataResultVo.setValue(null);
            }
            dataResultVo.setCode(CustomSelectionConstant.SAT_SCORES);
        } else {
            dataResultVo = null;
        }
        return dataResultVo;
    }

    private void setInstructorName(DataResultVo vo, List<InstructorNameVO> instructorNameVOS) {
        if (instructorNameVOS != null && instructorNameVOS.size() > 0) {
            for (int i = 0; i < instructorNameVOS.size(); i++) {
                if (i == 0) {
                    vo.setValue(instructorNameVOS.get(i).getInstructorName());
                } else {
                    vo.setValue(vo.getValue() + "," + instructorNameVOS.get(i).getInstructorName());
                }
            }
        }

    }

    /**
     * 根据名称查询
     */
    @Override
    public List<CityVO> selectCityByName(QuzzyQueryVO quzzyQueryVO) {
        if (quzzyQueryVO.getName() == null) {
            throw new BaseException("查询条件不能为空");
        }
        return studentMapper.selectCityByName(quzzyQueryVO);
    }

    /**
     * 跟新站点信息
     */
    @Override
    public void updateCityBySn(updatecityQO updatecityQO) {
        if (updatecityQO.getSn() == null) {
            throw new BaseException("学号不能为空");
        }
        if (updatecityQO.getDeparturePlace() == null) {
            throw new BaseException("出发地不能为空");
        }
        if (updatecityQO.getDestinationPlace() == null) {
            throw new BaseException("目的地不能为空");
        }
        studentMapper.updateCityBySn(updatecityQO);

    }


    /**
     * 根据学号查询学籍异动学生
     */
    @Override
    public PageResult<XjydVO> queryXJYDList(XjydQo xjydQo) {
        PageHelper.startPage(xjydQo.getPgCt(), xjydQo.getPgSz());
        Page<XjydVO> page = (Page<XjydVO>) studentMapper.queryXJYDList(xjydQo);
        PageHelper.clearPage();
        return new MybatisPageResult<>(page);
    }

    /**
     * 查询简单学生数据给咨询管理
     * 2019年11月28日13:55:24
     * author：666
     *
     * @return
     */
    @Override
    public PageInfo findSimpStuInf2TalkManage(TKQuery tkQuery) {
        return PageHelper.offsetPage(tkQuery.getPage(), tkQuery.getSize()).doSelectPageInfo(() -> {
            studentMapper.selectSimInfByColAndGrade(tkQuery.getCollegeCode(), tkQuery.getGradeName(),
                    tkQuery.getStuSn(), tkQuery.getStuName(), tkQuery.getMajorCode(), tkQuery.getClassCode());
        });
    }
}