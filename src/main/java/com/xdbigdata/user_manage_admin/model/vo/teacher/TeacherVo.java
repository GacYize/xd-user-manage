package com.xdbigdata.user_manage_admin.model.vo.teacher;

import com.xdbigdata.user_manage_admin.model.RoleModel;
import com.xdbigdata.user_manage_admin.model.UserModel;
import lombok.Data;

import java.util.List;

/**
 * @auther caijiang
 * @data 2018/11/8
 */
@Data
public class TeacherVo {

    /**
     * 用户信息
     */
    private UserModel userModel;
    /**
     * 角色信息
     */
    private List<RoleModel> roleModelList;

}
