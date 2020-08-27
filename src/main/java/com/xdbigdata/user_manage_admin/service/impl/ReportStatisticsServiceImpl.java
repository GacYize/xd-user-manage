package com.xdbigdata.user_manage_admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xdbigdata.user_manage.domain.Role;
import com.xdbigdata.user_manage.dto.RoleAndInfoDto;
import com.xdbigdata.user_manage_admin.mapper.ReportStatisticsMapper;
import com.xdbigdata.user_manage_admin.model.domain.Report;
import com.xdbigdata.user_manage_admin.model.qo.ReportQo;
import com.xdbigdata.user_manage_admin.model.vo.StudentReportVo;
import com.xdbigdata.user_manage_admin.service.ReportStatisticsService;
import com.xdbigdata.user_manage_admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @Author: ghj
 * @Date: 2019/12/4 11:01
 * @Version 1.0
 */
@Service
@Slf4j
public class ReportStatisticsServiceImpl implements ReportStatisticsService {
    @Autowired
    private ReportStatisticsMapper reportStatisticsMapper;


    @Override
    public PageInfo<StudentReportVo> getReportByQuery(ReportQo reportQo) {
        PageHelper.startPage(reportQo.getPgCt(), reportQo.getPgSz());
        RoleAndInfoDto session = SessionUtil.getSession();
        String sn = session.getSn();
        Long roleId = session.getRoleList().get(0).getId();
        reportQo.setLoginSn(sn);
        reportQo.setLoginRoleId(roleId);
        List<StudentReportVo> studentReportVoList = reportStatisticsMapper.findByQuery(reportQo);
        return new PageInfo<>(studentReportVoList);
    }

    @Override
    public List<Report> findReportItemById(Long id) {
        return reportStatisticsMapper.findReportItemById(id);
    }
}
