package com.xdbigdata.user_manage_admin.service;


import com.xdbigdata.framework.common.model.PageResult;
import com.xdbigdata.user_manage_admin.enums.AuditStatus;
import com.xdbigdata.user_manage_admin.model.qo.student.StudentApplyListQo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentApplyListVo;

/**
 * StudentModifyAudit service interface
 *
 * @author lshaci
 */
public interface StudentModifyAuditService {

    /**
     * 获取审核列表
     *
     * @param query 审核列表查询对象
     * @return 申请列表
     */
    PageResult<StudentApplyListVo> getAuditList(StudentApplyListQo query);
    /**
     * 驳回
     *
     * @param id 申请id
     * @param auditStatus 申请结果
     * @param reason 审核意见
     */
    void audit(Long id, AuditStatus auditStatus, String reason);
}