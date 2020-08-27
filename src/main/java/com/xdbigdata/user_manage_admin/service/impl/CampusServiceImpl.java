package com.xdbigdata.user_manage_admin.service.impl;
import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.user_manage_admin.annotation.OperateLog;
import com.xdbigdata.user_manage_admin.constant.StatusConstant;
import com.xdbigdata.user_manage_admin.model.dto.campus.CampusDto;
import com.xdbigdata.user_manage_admin.mapper.CampusMapper;
import com.xdbigdata.user_manage_admin.model.CampusModel;
import com.xdbigdata.user_manage_admin.model.qo.campus.AddCampusQo;
import com.xdbigdata.user_manage_admin.model.qo.campus.EditCampusQo;
import com.xdbigdata.user_manage_admin.service.CampusService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.model.vo.campus.AddCampusVo;
import com.xdbigdata.user_manage_admin.model.vo.campus.EditCampusVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampusServiceImpl implements CampusService {

	@Autowired
	private CampusMapper campusMapper;

	@Override
	@Transactional
	@OperateLog(module = "综合管理")
	public AddCampusVo addCampus(AddCampusQo addCampusQo) {
		CampusModel campusModel = verifyCampusName(addCampusQo.getName());
		
		campusModel.setAddress(addCampusQo.getAddress().trim());
		campusModel.setStatus(StatusConstant.STATUS_VALID);
		campusModel.setCreateTime(new Date());
		campusModel.setCreateUserId(ContextUtil.getId());
		
		int i = campusMapper.insertSelective(campusModel);
		if (i != 1) {
			throw new BaseException("保存校区失败");
		}
		ContextUtil.LOG_THREADLOCAL.set("添加校区");
		return null;
	}

	@Override
	@Transactional
	@OperateLog(module = "综合管理")
	public EditCampusVo editCampus(EditCampusQo editCampusQo) {
		CampusModel campusModel = verifyCampusName(editCampusQo.getName());
		BeanUtils.copyProperties(editCampusQo, campusModel);
		
		campusModel.setUpdateTime(new Date());
		campusModel.setUpdateUserId(ContextUtil.getId());
		
		int i = campusMapper.updateByPrimaryKeySelective(campusModel);
		if (i != 1) {
			throw new BaseException("修改校区失败");
		}
		ContextUtil.LOG_THREADLOCAL.set("修改校区");
		return null;
	}

	@Override
	public List<CampusDto> listCampus() {
		return campusMapper.selectAll().stream().map(m -> {
			CampusDto dto = new CampusDto();
			BeanUtils.copyProperties(m, dto);
			return dto;
		}).collect(Collectors.toList());
	}

	/**
	 * 验证校区名称是否重复
	 * 
	 * @param campusName 校区名称
	 * @return
	 */
	private CampusModel verifyCampusName(String campusName) {
		CampusModel campusModel = new CampusModel();
		campusModel.setName(campusName.trim());
		List<CampusModel> dbCampus = campusMapper.select(campusModel);
		if (CollectionUtils.isNotEmpty(dbCampus)) {
			throw new BaseException("校区名称重复");
		}
		return campusModel;
	}
}