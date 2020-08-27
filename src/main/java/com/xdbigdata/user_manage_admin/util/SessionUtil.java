package com.xdbigdata.user_manage_admin.util;


import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.framework.web.utils.SessionUserUtils;
import com.xdbigdata.user_manage.domain.Role;
import com.xdbigdata.user_manage.dto.RoleAndInfoDto;
import com.xdbigdata.user_manage_admin.constant.CommonConstant;
import com.xdbigdata.user_manage_admin.model.dto.login.LoginUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by staunch on 2016-07-21.
 * version：v1.0
 * instruction：初始版本
 */
@Component
@Slf4j
public class SessionUtil {


    public  static  void getLoginUser(){
        RoleAndInfoDto userInSession = new RoleAndInfoDto();
        List<Role> roleList1 = new ArrayList<>();
        Role role1 = new Role();
        userInSession.setName("万成香");//04827 朱皆笑
        userInSession.setSn("1994962");
        role1.setId(2L);
        role1.setName("SCHOOL");
        roleList1.add(role1);
        userInSession.setRoleList(roleList1);
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        requestAttributes.getRequest().getSession().setAttribute(CommonConstant.USER_IN_SESSION,userInSession);

    }

    public static void login(RoleAndInfoDto roleAndInfoDto) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        requestAttributes.getRequest().getSession().setAttribute(CommonConstant.USER_IN_SESSION,roleAndInfoDto);
    }

    /**
     * 获取session
     */
    public static RoleAndInfoDto getSession() {
        //getLoginUser();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        RoleAndInfoDto userInSession = (RoleAndInfoDto) requestAttributes.getRequest().getSession().
                getAttribute(CommonConstant.USER_IN_SESSION);
        if (userInSession == null) {
            throw new BaseException("登录过期");
//          userInSession = new RoleAndInfoDto();
//            List<Role> roleList1 = new ArrayList<>();
//            Role role1 = new Role();
//            //学工
//            userInSession.setName("万成香");//04827 朱皆笑
//            userInSession.setSn("19949602");
//            role1.setId(2L);
//            role1.setName("学校用户");
//            //单位用户 05575
////            userInSession.setName("张苗苗");
////            userInSession.setSn("04407");// 05575 陈崇 05576 张苗苗
////            role1.setId(4L);
////            role1.setName("责任辅导员");
//
//            //学生
////            userInSession.setName("窦**");
////            userInSession.setSn("201806990103");//201624450106 王心怡 201502070111 窦**
////            role1.setId(4L);
////            role1.setName("学生");
//
//            roleList1.add(role1);
//            userInSession.setRoleList(roleList1);
        }
        return userInSession;
    }

}
