package com.xdbigdata.user_manage_admin.model.dto.student;

import lombok.Data;

/**
 * @author huyuanjia
 * @date 2018/11/30 14:50
 */
@Data
public class PunishStudentPO {
    private Long id;

    private String sn;

    private String studentName;

    private Long collegeId;

    private String collegeName;

    private Long majorId;

    private String majorName;

    private String gradeName;

    private Long classId;

    private String className;

    private Long illegalRoleId;

    private String illegalRoleName;

    private Long illegalTypeId;

    private Long illegalTypeLevel2Id;

    private String illegalTypeName;

    private String illegalTypeLevel2Name;

    private Long punishResultId;

    private String punishResultName;

    private Integer punishTerm;

    private String doSn;

    private String doName;

    private String createDate;

    private String updateDate;

    private String waitCancelDate;

    private Integer state;

    private String cancelSn;

    private String cancelName;

    private String extendSn;

    private String extendName;

    private String punishFileNum;

    private String briefIntroduction;
}
