package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.domain.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictionaryMapper extends TKMapper<Dictionary> {

    /**
     * 获取所有的数据字典(包含类型名称)
     */
    List<Dictionary> selectAllWithTypeName();

    List<Dictionary>  selectByTypeName(String 学生类型);

    /**
     *
     * @param id
     * @return
     */
    Integer findById(@Param("id") Long id);
}