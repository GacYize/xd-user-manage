package com.xdbigdata.user_manage_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.framework.excel.exception.ExcelHandlerException;
import com.xdbigdata.framework.excel.handler.POIExcelUploadHandler;
import com.xdbigdata.user_manage_admin.annotation.OperateLog;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.constant.StatusConstant;
import com.xdbigdata.user_manage_admin.mapper.PoliticStatusMapper;
import com.xdbigdata.user_manage_admin.mapper.RoleMapper;
import com.xdbigdata.user_manage_admin.mapper.UserMapper;
import com.xdbigdata.user_manage_admin.mapper.UserRoleMapper;
import com.xdbigdata.user_manage_admin.model.PoliticStatusModel;
import com.xdbigdata.user_manage_admin.model.RoleModel;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.UserRoleModel;
import com.xdbigdata.user_manage_admin.model.dto.manager.BatchGrantTeacherDto;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerInfoDto;
import com.xdbigdata.user_manage_admin.model.qo.teacher.AddAndUpdateTeacherQo;
import com.xdbigdata.user_manage_admin.model.vo.manager.ManagerInfoVo;
import com.xdbigdata.user_manage_admin.model.vo.teacher.TeacherVo;
import com.xdbigdata.user_manage_admin.service.TeacherService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.util.OriginPlaceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PoliticStatusMapper politicStatusMapper;

    @Override
    @OperateLog(module = "人员管理")
    public UserModel addOrUpdate(AddAndUpdateTeacherQo addTeacherQo) {
        String sn = StringUtils.trimToEmpty(addTeacherQo.getSn());
        UserModel userModel = userMapper.selectUserBySn(sn);
        if (addTeacherQo.getId() == null && userModel != null) {
            throw new BaseException("工号为" + sn + "已经存在");
        }
        userModel = new UserModel();
        BeanUtils.copyProperties(addTeacherQo, userModel);
        userModel.setValid(1);
        if (addTeacherQo.getId() == null) {
            userModel.setPassword("1234");
            userMapper.insert(userModel);
        } else {
            userMapper.updateByPrimaryKey(userModel);
        }
        Long userId = userModel.getId();
        List<Long> roleIds = addTeacherQo.getRoleIds();
        UserRoleModel userRoleModel;
        List<UserRoleModel> userRoleModels = Lists.newArrayList();
        if (addTeacherQo.getId() == null) {
            ContextUtil.LOG_THREADLOCAL.set("新增教师");
        } else {
            ContextUtil.LOG_THREADLOCAL.set("修改教师");
        }
        if (addTeacherQo.getId() != null || CollectionUtils.isEmpty(roleIds)) {
            return userModel;
        }
        for (Long roleId : roleIds) {
            userRoleModel = new UserRoleModel();
            userRoleModel.setRoleId(roleId);
            userRoleModel.setUserId(userId);
            userRoleModel.setJoinTime(new Date());
            userRoleModel.setValid(1);
            userRoleModels.add(userRoleModel);
        }
        userRoleMapper.insertList(userRoleModels);
        return userModel;
    }

    @Override
    @OperateLog(module = "人员管理")
    public ResultBean<Object> batchGrantTeacher(MultipartFile file, Long[] roles) throws Exception {
        ResultBean<Object> result = new ResultBean<>();
        result.setStatus(false);
        if (ArrayUtils.isEmpty(roles)) {
            result.setMessage("授权角色不能为空");
            return result;
        }
        if (file == null || file.isEmpty()) {
            result.setMessage("上传文件不能为空");
            return result;
        }
        long size = file.getSize();
        if (size > 8192000) {
            result.setMessage("文件大小超出限制");
            return result;
        }
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!Objects.equals("xls", suffix) && !Objects.equals("xlsx", suffix)) {
            result.setMessage("文件格式不正确");
            return result;
        }
        List<BatchGrantTeacherDto> teachers = null;
        try {
            teachers = POIExcelUploadHandler.excel2Entities(file.getInputStream(), 1, BatchGrantTeacherDto.class);
        } catch (ExcelHandlerException e) {
            throw new BaseException("请按照模板输入完整正确的学工号和姓名");
        } catch (Exception e) {
            throw new BaseException("请使用正确的Excel文件");
        }
        if (CollectionUtils.isEmpty(teachers)) {
            result.setMessage("无教师数据导入");
            return result;
        }

        List<String> allNewSns = new ArrayList<>();
        List<String> repeatSns = new ArrayList<>();
        teachers.stream().collect(Collectors.groupingBy(BatchGrantTeacherDto::getSn)).forEach((sn, teacherList) -> {
            allNewSns.add(sn);
            if (teacherList.size() > 1) {
                repeatSns.add(sn);
            }
        });
        if (CollectionUtils.isNotEmpty(repeatSns)) {
            result.setCode(101);
            result.setData(repeatSns);
            result.setMessage("导入数据中存在重复");
            return result;
        }

        List<UserModel> dbUsers = userMapper.findIdAndSnBySnS(allNewSns, Arrays.asList(roles));
        if (CollectionUtils.isNotEmpty(dbUsers)) {
            result.setCode(102);
            result.setData(dbUsers.stream().map(UserModel::getSn).collect(Collectors.toList()));
            result.setMessage("导入数据与已有数据存在重复");
            return result;
        }

        List<UserModel> needSaveUsers = teachers.stream().map(t -> {
            UserModel user = new UserModel();
            user.setSn(t.getSn());
            user.setName(t.getName());
            user.setValid(StatusConstant.STATUS_VALID);
            return user;
        }).collect(Collectors.toList());

//        userMapper.addUserList(needSaveUsers);
//        List<UserModel> newUsers = userMapper.findIdAndSnBySnS(allNewSns, Arrays.asList(roles));
        List<UserModel> newUsers = userMapper.findBySnList(allNewSns);
        List<UserRoleModel> userRoles = newUsers.stream().flatMap(u -> {
            return Arrays.stream(roles).map(roleId -> {
                UserRoleModel userRoleModel = new UserRoleModel();
                userRoleModel.setRoleId(roleId);
                userRoleModel.setUserId(u.getId());
                userRoleModel.setJoinTime(new Date());
                userRoleModel.setValid(1);
                return userRoleModel;
            });
        }).collect(Collectors.toList());

        userRoleMapper.insertList(userRoles);

        result.setStatus(true);
        result.setMessage("批量授权教师成功");
        ContextUtil.LOG_THREADLOCAL.set("批量授权教师");
        return result;
    }

    @Override
    public TeacherVo findBySn(String sn) {
        TeacherVo teacherVo = new TeacherVo();
        UserModel userModel = userMapper.selectUserBySn(sn);
        teacherVo.setUserModel(userModel);
        List<RoleModel> roleModelList = roleMapper.findBySn(sn);
        teacherVo.setRoleModelList(roleModelList);
        return teacherVo;
    }

    @Override
    public ManagerInfoVo findInfoBySn(String sn) {
        ManagerInfoVo managerInfoVo = userMapper.selectUserBySn2(sn);
        //获取籍贯编码
        String originCode = managerInfoVo.getOriginCode();
        managerInfoVo.setOriginCodeName(OriginPlaceUtils.getName(originCode));
        Integer politicStatusId = managerInfoVo.getPoliticalStatus();
        if (politicStatusId != null) {
            PoliticStatusModel politicStatusModel = new PoliticStatusModel();
            politicStatusModel.setId(Long.valueOf(politicStatusId));
            PoliticStatusModel politicStatusModel1 = politicStatusMapper.selectByPrimaryKey(politicStatusModel);
            managerInfoVo.setPoliticalStatusName(politicStatusModel1.getName());
        }
        return managerInfoVo;
    }

    @Override
    public void update(ManagerInfoDto managerInfoDto) {
        UserModel model = userMapper.selectUserBySn(ContextUtil.getSn());
        BeanUtils.copyProperties(managerInfoDto, model);
        //更新user表
        userMapper.updateByPrimaryKeySelective(model);
    }

    /**
     * 根据sn查询姓名
     *
     * @param sn
     * @return
     */
    @Override
    public String findNameBySn(String sn) {
        ManagerInfoVo managerInfoVo = userMapper.selectUserBySn2(sn);
        String name = null;
        if (managerInfoVo != null) {
            name = managerInfoVo.getName();
        }
        return name;
    }

    /**
     * 根据学院查询所有辅导员信息
     *
     * @param College
     * @param page
     * @param size
     * @return
     */
    @Override
    public Object listIns(Long College, String search, Integer page, Integer size) {
        return PageHelper.offsetPage(page, size).doSelectPageInfo(() -> {
            userMapper.selectUserByCollege(College);
        });
    }
}