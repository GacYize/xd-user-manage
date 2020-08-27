package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.domain.StudentBasic;

public interface StudentBasicMapper extends TKMapper<StudentBasic> {

    /**
     * 根据学号查询学生基本信息
     *
     * @param sn 学号
     * @return 学生基本信息
     */
    StudentBasic selectBySn(String sn);
}