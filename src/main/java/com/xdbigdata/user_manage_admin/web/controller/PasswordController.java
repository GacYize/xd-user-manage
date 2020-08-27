package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.model.dto.UpdatePasswordDto;
import com.xdbigdata.user_manage_admin.service.UserService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/password")
@Api(value = "用户密码相关控制层", tags = "用户密码相关")
public class PasswordController {

    @Autowired
    private UserService userService;

    @ApiOperation("修改密码")
    @PostMapping("/update")
    public JsonResponse<Object> updatePassword(@Valid @RequestBody UpdatePasswordDto updatePasswordDto) {
        String sn = ContextUtil.getLoginSn();
        updatePasswordDto.setSn(sn);
        userService.updatePassword(updatePasswordDto);
        return JsonResponse.successMessage("密码修改成功");
    }

}
