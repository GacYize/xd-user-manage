package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.dto.operatelog.OperateLogDto;
import com.xdbigdata.user_manage_admin.model.OperateLogModel;
import com.xdbigdata.user_manage_admin.model.qo.operatelog.ListOperateLogQo;

import java.util.List;

public interface OperateLogMapper extends TKMapper<OperateLogModel> {

	/**
	 * 根据页面条件查询操作日志
	 * 
	 * @param listOperateLogQo 页面查询条件
	 * @return
	 */
	List<OperateLogDto> selectByQuery(ListOperateLogQo listOperateLogQo);
}