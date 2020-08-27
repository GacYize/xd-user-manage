package com.xdbigdata.user_manage_admin.service;

import com.github.pagehelper.PageInfo;
import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.model.domain.Report;
import com.xdbigdata.user_manage_admin.model.domain.ReportStatistics;
import com.xdbigdata.user_manage_admin.model.qo.ReportQo;
import com.xdbigdata.user_manage_admin.model.vo.StudentReportVo;

import java.util.List;

/**
 * @Author: ghj
 * @Date: 2019/12/4 11:00
 * @Version 1.0
 */
public interface ReportStatisticsService {
    /**
     * 条件筛选成绩列表
     * @param reportQo
     * @return
     */
    PageInfo<StudentReportVo> getReportByQuery(ReportQo reportQo);

    /**
     * 根据统计id获取详情
     * @param id
     * @return
     */
    List<Report> findReportItemById(Long id);
}
