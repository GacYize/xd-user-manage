package com.xdbigdata.user_manage_admin.service;

import java.util.List;

import com.xdbigdata.user_manage_admin.model.dto.campus.CampusDto;
import com.xdbigdata.user_manage_admin.model.qo.campus.AddCampusQo;
import com.xdbigdata.user_manage_admin.model.qo.campus.EditCampusQo;
import com.xdbigdata.user_manage_admin.model.vo.campus.AddCampusVo;
import com.xdbigdata.user_manage_admin.model.vo.campus.EditCampusVo;

public interface CampusService{

	AddCampusVo addCampus(AddCampusQo addCampusQo);

	EditCampusVo editCampus(EditCampusQo editCampusQo);

	List<CampusDto>  listCampus();


}