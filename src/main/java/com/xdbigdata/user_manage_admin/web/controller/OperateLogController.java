package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.annotation.NeedRole;
import com.xdbigdata.user_manage_admin.bean.ResultBean;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.model.qo.operatelog.ListOperateLogQo;
import com.xdbigdata.user_manage_admin.service.OperateLogService;
import com.xdbigdata.user_manage_admin.model.vo.operatelog.ListOperateLogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人:huangjianfeng
 * @简要描述:操作日志管理
 * @创建时间: 2018/10/25 10:49
 * @参数:
 * @返回:
 */
@RestController
@NeedRole(UserTypeConstant.ADMIN)
@RequestMapping("/api/operate_log")
@Api(value = "综合管理 - 操作日志", tags = "综合管理 - 操作日志")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    @ApiOperation(value = "获取操作日志列表", notes = "操作日志列表接口")
    @PostMapping(value = "list", produces = "application/json;charset=UTF-8")
    public ResultBean<ListOperateLogVo> listOperateLog(@RequestBody ListOperateLogQo listOperateLogQo) {
        ListOperateLogVo listOperateLogVo = operateLogService.listOperateLog(listOperateLogQo);
        return new ResultBean<>(listOperateLogVo);
    }

}