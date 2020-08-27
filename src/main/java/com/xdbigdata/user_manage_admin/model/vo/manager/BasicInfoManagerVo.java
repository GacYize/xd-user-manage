package com.xdbigdata.user_manage_admin.model.vo.manager;
import com.xdbigdata.user_manage_admin.model.vo.role.ManagerRoleVo;
import lombok.Data;

import java.util.List;

@Data
public class BasicInfoManagerVo {

    private Long id;
    private String sn;
    private String name;
    private List<ManagerRoleVo> managerRoles;
}