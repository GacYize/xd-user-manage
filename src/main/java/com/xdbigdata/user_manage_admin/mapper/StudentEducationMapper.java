package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.domain.StudentBasic;
import com.xdbigdata.user_manage_admin.model.domain.StudentEducation;
import org.apache.ibatis.annotations.Param;

public interface StudentEducationMapper extends TKMapper<StudentEducation> {

    /**
     * 根据学号查询学生的学籍信息
     *
     * @param sn 学号
     * @return 学生的学籍信息
     */
    StudentEducation selectBySn(@Param("sn") String sn);
}