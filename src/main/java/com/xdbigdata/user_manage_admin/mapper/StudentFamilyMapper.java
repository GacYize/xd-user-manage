package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.domain.StudentBasic;
import com.xdbigdata.user_manage_admin.model.domain.StudentFamily;

import java.util.List;

public interface StudentFamilyMapper extends TKMapper<StudentFamily> {

    /**
     * 根据sn查询学生家庭信息
     *
     * @param sn 学号
     * @return 学生家庭信息集合
     */
    List<StudentFamily> selectBySn(String sn);

    /**
     * 根据学号删除学生的家庭信息
     *
     * @param sn 学号
     */
    void deleteBySn(String sn);
}