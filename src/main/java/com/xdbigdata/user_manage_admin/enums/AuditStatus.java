package com.xdbigdata.user_manage_admin.enums;

import lombok.Getter;

/**
 * 审核状态
 */
@Getter
public enum AuditStatus {

    /**
     * 待审核
     */
    WAIT(0, "待审核"),
    /**
     * 通过
     */
    PASS(1, "通过"),
    /**
     * 驳回
     */
    REJECT(-1, "驳回"),
    ;

    private Integer type;
    private String name;

    private AuditStatus(Integer type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * 根据审核类型获取描述字符串
     *
     * @param type 审核类型
     * @return 描述字符串
     */
    public static String getAuditStatus(Integer type) {
        if (type == null) {
            return null;
        }

        for (AuditStatus status: AuditStatus.values()) {
            if (status.getType() == type) {
                return status.name;
            }
        }

        return null;
    }
}