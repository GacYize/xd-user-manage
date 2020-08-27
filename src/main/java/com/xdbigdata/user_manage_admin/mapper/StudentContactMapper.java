package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.domain.StudentBasic;
import com.xdbigdata.user_manage_admin.model.domain.StudentContact;

public interface StudentContactMapper extends TKMapper<StudentContact> {

    /**
     * 根据sn查询学生联系信息
     *
     * @param sn 学号
     * @return 学生联系信息
     */
    StudentContact selectBySn(String sn);
}