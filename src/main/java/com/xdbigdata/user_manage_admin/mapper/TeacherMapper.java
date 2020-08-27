package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.TeacherModel;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper extends TKMapper<TeacherModel> {

    int deleteByUserId(@Param("userId") Long userId);

}