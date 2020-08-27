package com.xdbigdata.user_manage_admin.service.impl;

import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.user_manage_admin.mapper.UserMapper;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.dto.UpdatePasswordDto;
import com.xdbigdata.user_manage_admin.model.vo.QuzzyQueryVO;
import com.xdbigdata.user_manage_admin.model.vo.TeachersVO;
import com.xdbigdata.user_manage_admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordDto updatePasswordDto) {
        UserModel user = userMapper.selectUserBySn(updatePasswordDto.getSn());
        if (Objects.isNull(user)) {
            throw new BaseException("当前登录用户信息错误");
        }
        if (!Objects.equals(updatePasswordDto.getOldPassword(), user.getPassword())) {
            throw new BaseException("旧密码输入错误");
        }
        user.setPassword(updatePasswordDto.getNewPassword());
        userMapper.updateByPrimaryKeySelective(user);
        log.info("{}:{} 用户修改了密码.", user.getSn(), user.getName());
    }


    /**
     * 根据组织机构Id获取对应的教职工
     *
     * @return
     */
    @Override
    public List<TeachersVO> getInstructor(Long organizationId, String sn) {
        if (organizationId == null) {
            throw new BaseException("组织机构Id不能为空");
        }
        return userMapper.getInstructor(organizationId, sn);
    }

    /**
     * 模糊匹配教师明细
     *
     * @param quzzyQueryVO
     * @return
     */
    @Override
    public List<TeachersVO> fuzzyQueryUser(QuzzyQueryVO quzzyQueryVO) {
        if (quzzyQueryVO.getName() == null) {
            throw new BaseException("姓名或学号不能为空");
        }
        return userMapper.fuzzyQueryUser(quzzyQueryVO);
    }

    /**
     * 为班级添加辅导员
     */
    @Override
    public void addInstructor(TeachersVO teachersVO) {
        if (teachersVO.getOrganizationId() == null) {
            throw new BaseException("组织机构id不能为空");
        }
        if (teachersVO.getUserId() == null) {
            throw new BaseException("辅导员id不能为空");
        }
        if (teachersVO.getRoleId() == null) {
            throw new BaseException("辅导员角色id不能为空");
        }
        Integer i = userMapper.repeatInstructor(teachersVO);
        if (i != null && i.intValue() > 0) {
            throw new BaseException("不能重复添加");
        }
        userMapper.addInstructor(teachersVO);
    }

    /**
     * 移除辅导员
     */
    @Override
    public void deleteInstructor(TeachersVO teachersVO) {
        if (teachersVO.getOrganizationId() == null) {
            throw new BaseException("组织机构id不能为空");
        }
        if (teachersVO.getUserId() == null) {
            throw new BaseException("辅导员id不能为空");
        }
        if (teachersVO.getRoleId() == null) {
            throw new BaseException("辅导员角色id不能为空");
        }
        userMapper.deleteInstructor(teachersVO);
    }

}
