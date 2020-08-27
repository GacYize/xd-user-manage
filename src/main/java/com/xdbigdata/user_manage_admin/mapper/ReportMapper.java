package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.domain.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportMapper extends TKMapper<Report> {

    Report findBySn(@Param("sn") String sn);

    /**
     * 获取所有学期
     * @return
     */
    List<Integer> findGroupBySemester();
}