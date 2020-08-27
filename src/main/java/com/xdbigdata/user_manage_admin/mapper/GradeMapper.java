package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.GradeModel;

import java.util.List;

/**
 * @auther caijiang
 * @data 2018/11/9
 */
public interface GradeMapper extends TKMapper<GradeModel> {

    /**
     * 获取所有年级
     * @return
     */
    List<GradeModel> findAllGrade();

}
