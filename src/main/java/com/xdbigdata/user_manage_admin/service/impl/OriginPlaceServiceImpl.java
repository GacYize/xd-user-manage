package com.xdbigdata.user_manage_admin.service.impl;

import com.xdbigdata.user_manage_admin.mapper.OriginPlaceMapper;
import com.xdbigdata.user_manage_admin.model.OriginPlaceModel;
import com.xdbigdata.user_manage_admin.service.OriginPlaceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * OriginPlace service implement
 * 
 * @author lshaci
 */
@Service
@Slf4j
public class OriginPlaceServiceImpl implements OriginPlaceService {

    @Autowired
    private OriginPlaceMapper originPlaceMapper;

    @Override
    public List<OriginPlaceModel> getByParentCode(String parentCode) {
        if (StringUtils.isBlank(parentCode)) {
            return null;
        }
        OriginPlaceModel condition = new OriginPlaceModel();
        condition.setParentCode(parentCode);
        return originPlaceMapper.select(condition);
    }
}
