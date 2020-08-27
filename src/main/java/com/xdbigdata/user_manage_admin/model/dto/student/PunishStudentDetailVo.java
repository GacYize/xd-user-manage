package com.xdbigdata.user_manage_admin.model.dto.student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PunishStudentDetailVo extends PunishStudentPO {

    private String illegalTypeName;

    private String illegalTypeLevel2Name;

    List<UpFileInfoVo> upFileInfoVos;
}
