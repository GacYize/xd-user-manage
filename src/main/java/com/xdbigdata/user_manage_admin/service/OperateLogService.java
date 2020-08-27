package com.xdbigdata.user_manage_admin.service;


import com.xdbigdata.user_manage_admin.model.qo.operatelog.ListOperateLogQo;
import com.xdbigdata.user_manage_admin.model.vo.operatelog.ListOperateLogVo;

public interface OperateLogService{


    ListOperateLogVo listOperateLog(ListOperateLogQo listOperateLogQo);

    void addOperateLog(String module,String detail);

}