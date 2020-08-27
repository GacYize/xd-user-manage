package com.xdbigdata.user_manage_admin.service;

import com.xdbigdata.user_manage_admin.model.domain.Dictionary;
import com.xdbigdata.user_manage_admin.model.vo.DictionaryTypeVo;

import java.util.List;
import java.util.Map;

public interface DictionaryService{

    /**
     * 获取所有的数据字典下拉信息
     */
    Map<String, List<Dictionary>> getAll();

    /**
     *
     * @return
     */
//    DictionaryTypeVo getDictionaryType();
}