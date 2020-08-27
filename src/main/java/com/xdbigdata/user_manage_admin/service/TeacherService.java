package com.xdbigdata.user_manage_admin.service;


import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerInfoDto;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.vo.manager.ManagerInfoVo;
import com.xdbigdata.user_manage_admin.model.vo.teacher.TeacherVo;
import org.springframework.web.multipart.MultipartFile;

import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.model.qo.teacher.AddAndUpdateTeacherQo;

import java.util.List;

public interface TeacherService {

    /**
     * 新增、修改教师基本信息
     * @param addTeacherQo
     */
    UserModel addOrUpdate(AddAndUpdateTeacherQo addTeacherQo);

	ResultBean<Object> batchGrantTeacher(MultipartFile file, Long[] roles) throws Exception;

    /**
     * 根据sn获取教师信息
     * @param sn
     * @return
     */
    TeacherVo findBySn(String sn);
    /**
     * 根据sn获取辅导员个人信息
     * @param sn
     * @return
     */
    ManagerInfoVo findInfoBySn(String sn);

    /**
     * 修改辅导员个人信息
     * @param managerInfoDto
     */
    void update(ManagerInfoDto managerInfoDto);

    /**
     * 根据sn查询姓名
     *
     * @param sn
     * @return
     */
    String findNameBySn(String sn);

    /**
     * 根据学院查询所有辅导员信息
     *
     * @return
     */
    Object listIns(Long College, String search, Integer page, Integer size);
}