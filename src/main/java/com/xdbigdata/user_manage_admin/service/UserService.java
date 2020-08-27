package com.xdbigdata.user_manage_admin.service;

import com.xdbigdata.user_manage_admin.model.dto.UpdatePasswordDto;
import com.xdbigdata.user_manage_admin.model.vo.QuzzyQueryVO;
import com.xdbigdata.user_manage_admin.model.vo.TeachersVO;

import java.util.List;

public interface UserService {

    /**
     * 修改密码
     *
     * @param updatePasswordDto
     */
    void updatePassword(UpdatePasswordDto updatePasswordDto);


    /**
     * 根据组织机构Id获取对应的教职工
     * @return
     */
    List<TeachersVO> getInstructor(Long organizationId, String sn);

    /**
     * 模糊匹配教师明细
     * @param quzzyQueryVO
     * @return
     */
    List<TeachersVO> fuzzyQueryUser(QuzzyQueryVO quzzyQueryVO);

    /**
     * 为班级添加辅导员
     */
    void addInstructor(TeachersVO teachersVO);

    /**
     * 移除辅导员
     */
    void deleteInstructor(TeachersVO teachersVO);

}
