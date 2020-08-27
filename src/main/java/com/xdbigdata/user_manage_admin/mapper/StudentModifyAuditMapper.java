package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.domain.StudentModifyAudit;
import com.xdbigdata.user_manage_admin.model.vo.SubmitModifyVo;
import com.xdbigdata.user_manage_admin.model.qo.student.StudentApplyListQo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentApplyListVo;

import java.util.List;

public interface StudentModifyAuditMapper extends TKMapper<StudentModifyAudit> {

    /**
     * 根据学号查询提交历史
     *
     * @param sn 学号
     * @return 提交历史集合
     */
    List<SubmitModifyVo> selectBySn(String sn);

    /**
     * 根据条件查询审核列表
     *
     * @param query 审核列表查询条件
     * @return 审核列表
     */
    List<StudentApplyListVo> selectList(StudentApplyListQo query);
}