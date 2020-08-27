package com.xdbigdata.user_manage_admin.util;

import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.framework.web.exception.LoginException;
import com.xdbigdata.framework.web.utils.HttpSessionUtils;
import com.xdbigdata.framework.web.utils.SessionUserUtils;
import com.xdbigdata.user_manage.domain.Role;
import com.xdbigdata.user_manage.dto.RoleAndInfoDto;
import com.xdbigdata.user_manage_admin.constant.CommonConstant;
import com.xdbigdata.user_manage_admin.model.dto.login.LoginUserDto;
import com.xdbigdata.user_manage_admin.enums.UserType;
import com.xdbigdata.user_manage_admin.mapper.StudentMapper;
import com.xdbigdata.user_manage_admin.mapper.UserMapper;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ContextUtil {

    public static ThreadLocal<String> LOG_THREADLOCAL = new ThreadLocal<>();

    public static UserMapper userMapper;
    public static UserService userService;
    public static StudentMapper studentMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        ContextUtil.userMapper = userMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        ContextUtil.userService = userService;
    }

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        ContextUtil.studentMapper = studentMapper;
    }
    //	*************************************************审核模块使用***************************************************

    /**
     * 用于修改密码获取登录用户sn
     */
    public static String getLoginSn() {
        RoleAndInfoDto roleAndInfo = (RoleAndInfoDto) HttpSessionUtils.getAttribute(CommonConstant.USER_IN_SESSION);
        if (roleAndInfo == null) {
            log.error("the user not in session");
            throw new LoginException("登录已失效，请重新登录");
        }

        List<Role> roleList = roleAndInfo.getRoleList();
        if (CollectionUtils.isEmpty(roleList)) {
            log.error("the role list is null");
            throw new BaseException("登录用户未选择角色");
        }

        Role role = roleList.get(0);
        String roleName = role.getName();
        if (StringUtils.isBlank(roleName)) {
            log.error("the role name is null");
            throw new BaseException("登录用户角色错误");
        }
        return roleAndInfo.getSn();
    }
    /**
     * 获取登录用户(session 共享中的RoleAndInfo)
     *
     * @return 登录用户
     */
    public static LoginUserDto getShare() {
        RoleAndInfoDto roleAndInfo = (RoleAndInfoDto) HttpSessionUtils.getAttribute(CommonConstant.USER_IN_SESSION);
//        		RoleAndInfoDto userInSession = new RoleAndInfoDto();
//        userInSession.setName("90466");
//        userInSession.setSn("90466");
//        List<Role> roleList1 = new ArrayList<>();
//        Role role1 = new Role();
//        role1.setId(2L);
//        role1.setName("学校用户");
//        roleList1.add(role1);
//        userInSession.setRoleList(roleList1);
//        roleAndInfo = userInSession;
        if (roleAndInfo == null) {
            log.error("the user not in session");
            throw new LoginException("登录已失效，请重新登录");
        }

        List<Role> roleList = roleAndInfo.getRoleList();
        if (CollectionUtils.isEmpty(roleList)) {
            log.error("the role list is null");
            throw new BaseException("登录用户未选择角色");
        }

        Role role = roleList.get(0);
        String roleName = role.getName();
        if (StringUtils.isBlank(roleName)) {
            log.error("the role name is null");
            throw new BaseException("登录用户角色错误");
        }
        UserType userType = UserType.getUserTypeByRole(roleName);
        if (userType == null) {
            log.error("Logged-in users who select roles cannot access the system");
            throw new BaseException("登录用户选择角色不能访问该系统");
        }

        LoginUserDto loginUser = new LoginUserDto();
        loginUser.setSn(roleAndInfo.getSn());
        loginUser.setName(roleAndInfo.getName());
        loginUser.setUserType(userType);
        loginUser.setType(userType.getType());
        loginUser.setRoleId(role.getId());
        loginUser.setRoleName(roleName);

        SessionUserUtils.setUserInSession(loginUser);
        SessionUserUtils.setUserRoleInSession(roleName);
        return loginUser;
    }

    /**
     * 获取登录用户
     *
     * @return 登录用户基本信息
     */
    public static LoginUserDto get() {
//        LoginUserDto loginUser = SessionUserUtils.getUserInSession(LoginUserDto.class);
//        if (loginUser == null) {
//            loginUser = getShare();
//        }
//        return loginUser;
        return getShare();
    }


    /**
     * 获取登录用户id
     *
     * @return 登录用户id
     */
    public static Long getId() {
        UserModel userModel = userMapper.selectUserBySn(getSn());
        return userModel.getId();
    }

    /**
     * 获取登录用户sn
     *
     * @return 登录用户sn
     */
    public static String getSn() {
        return get().getSn();
    }

    /**
     * 获取登录用户姓名
     *
     * @return 登录用户姓名
     */
    public static String getName() {
        return get().getName();
    }

    /**
     * 获取角色类型
     *
     * @return 登录角色类型
     */
    public static UserType getUserType() {
        return get().getUserType();
    }

    /**
     * 获取登录用户角色id
     *
     * @return 登录用户角色id
     */
    public static Long getRoleId() {
        return get().getRoleId();
    }

}
