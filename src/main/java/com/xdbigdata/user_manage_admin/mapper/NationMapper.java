package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.NationModel;

public interface NationMapper extends TKMapper<NationModel> {

    /**
     * 根据民族名称查询id
     *
     * @param nation 民族名称
     * @return 民族id
     */
    Long selectIdByName(String nation);
}
