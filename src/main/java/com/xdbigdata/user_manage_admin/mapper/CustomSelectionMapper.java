package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.domain.CustomSelection;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomSelectionMapper extends TKMapper<CustomSelection> {

    /**
     * @return
     */
    List<CustomSelection> queryCustomOrder();
}