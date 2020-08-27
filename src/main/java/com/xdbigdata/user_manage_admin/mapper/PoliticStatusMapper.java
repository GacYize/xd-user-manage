package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.PoliticStatusModel;
import org.springframework.stereotype.Repository;

public interface PoliticStatusMapper extends TKMapper<PoliticStatusModel> {

    /**
     * 根据数据字典id查询政治面貌对应的id
     *
     * @param politicsStatusId 数据字典中的政治面貌id
     * @return 政治面貌表中的id
     */
    Integer selectIdByDictionaryId(Long politicsStatusId);
}
