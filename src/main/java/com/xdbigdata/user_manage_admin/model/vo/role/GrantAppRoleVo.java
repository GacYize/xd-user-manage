package com.xdbigdata.user_manage_admin.model.vo.role;
import lombok.Data;

import java.util.List;

@Data
public class GrantAppRoleVo {

    private Long roleId;
    private List<Long> appIds;
    private List<String> appNames;

}