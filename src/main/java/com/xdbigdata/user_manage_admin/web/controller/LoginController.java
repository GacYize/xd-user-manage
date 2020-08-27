package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.framework.web.utils.SessionUserUtils;
import com.xdbigdata.user_manage.dto.RoleAndInfoDto;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.model.dto.login.LoginUserDto;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/user")
@Api(value = "用户相关控制层", tags = "用户相关")
public class LoginController {

    @ApiOperation("获取登录用户")
    @GetMapping("/getLoginUser")
    public ResultBean<LoginUserDto> getLoginUser() {
        LoginUserDto loginUser = ContextUtil.get();
        return new ResultBean<>(loginUser);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation("注销登录")
    @PostMapping("/logout")
    public ResultBean<Object> logout(HttpSession session) {
        session.invalidate();
        return ResultBean.createResultBean("注销登录成功");
    }

//    @ApiOperation("模拟登录")
//    @PostMapping("/loginTest")
//    public ResultBean<Object> login(@RequestBody LoginUserDto loginUserDto) {
//        SessionUserUtils.setUserInSession(loginUserDto);
//        SessionUserUtils.setUserRoleInSession(loginUserDto.getRoleName());
//        return ResultBean.createResultBean("登录成功");
//    }

    @ApiOperation(value = "模拟登录", notes = "模拟登录")
    @PostMapping(value = {"/login"})
    public JsonResponse login(@RequestBody RoleAndInfoDto roleAndInfoDto) throws Exception {
        SessionUtil.login(roleAndInfoDto);
        return JsonResponse.success("成功");
    }
}
