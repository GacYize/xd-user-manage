package com.xdbigdata.user_manage_admin.service;

import com.xdbigdata.user_manage_admin.model.OriginPlaceModel;

import java.util.List;

/**
 * OriginPlace service interface
 *
 * @author lshaci
 */
public interface OriginPlaceService  {

    /**
     * 根据父级地区编号获取下级地区
     *
     * @param parentCode 父级地区编号
     * @return 下级地区集合
     */
    List<OriginPlaceModel> getByParentCode(String parentCode);

}