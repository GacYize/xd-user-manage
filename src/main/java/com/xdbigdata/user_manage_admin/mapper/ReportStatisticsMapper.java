package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.domain.Report;
import com.xdbigdata.user_manage_admin.model.domain.ReportStatistics;
import com.xdbigdata.user_manage_admin.model.qo.ReportQo;
import com.xdbigdata.user_manage_admin.model.vo.StudentReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportStatisticsMapper extends TKMapper<ReportStatistics> {
    /**
     * 查询当前用户管理的学生成绩
     * @param reportQo
     * @return
     */
    List<StudentReportVo> findByQuery(ReportQo reportQo);
    /**
     * 根据id查询详情列表
     * @param id
     * @return
     */
    List<Report> findReportItemById(@Param("id") Long id);


}