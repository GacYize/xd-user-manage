package com.xdbigdata.user_manage_admin.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xdbigdata.framework.utils.DateUtils;
import com.xdbigdata.framework.utils.constants.Constants;
import com.xdbigdata.user_manage_admin.model.dto.login.LoginUserDto;
import com.xdbigdata.user_manage_admin.model.dto.operatelog.OperateLogDto;
import com.xdbigdata.user_manage_admin.mapper.OperateLogMapper;
import com.xdbigdata.user_manage_admin.model.OperateLogModel;
import com.xdbigdata.user_manage_admin.model.qo.operatelog.ListOperateLogQo;
import com.xdbigdata.user_manage_admin.service.OperateLogService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.model.vo.operatelog.ListOperateLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public ListOperateLogVo listOperateLog(ListOperateLogQo listOperateLogQo) {
		try {
			Date startTime = listOperateLogQo.getStartTime();
			if (startTime != null) {
				String format = DateUtils.formatShortDate(startTime) + " 00:00:00";
				listOperateLogQo.setStartTime(Constants.LONG_DATE_FORMATTER.parse(format));
			}
			Date endTime = listOperateLogQo.getEndTime();
			if (endTime != null) {
				String format = DateUtils.formatShortDate(endTime) + " 23:59:59";
				listOperateLogQo.setEndTime(Constants.LONG_DATE_FORMATTER.parse(format));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	PageHelper.startPage(listOperateLogQo.getPageNum(), listOperateLogQo.getPageSize()).setOrderBy("operate_time desc");
    	Page<OperateLogDto> page = (Page<OperateLogDto>) operateLogMapper.selectByQuery(listOperateLogQo);
    	
        return new ListOperateLogVo(page, page.getResult());
    }

    @Override
    public void addOperateLog(String module, String detail) {
        LoginUserDto loginUser = ContextUtil.get();
        OperateLogModel operateLogModel = new OperateLogModel();
        operateLogModel.setModule(module);
        operateLogModel.setRoleName(loginUser.getRoleName());
        operateLogModel.setOperatorId(loginUser.getId());
        operateLogModel.setOperatorName(loginUser.getName());
        operateLogModel.setOperateTime(new Date());
        operateLogModel.setDetail(detail);
        operateLogMapper.insert(operateLogModel);
    }
}