package com.xdbigdata.user_manage_admin.enums;

import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户类型
 */
@Getter
public enum UserType {
    /**
     * 学生
     */
    STUDENT(1, UserTypeConstant.STUDENT),
    /**
     * 学校用户
     */
    SCHOOL(2, UserTypeConstant.SCHOOL),
    /**
     * 学院用户
     */
    COLLEGE(3, UserTypeConstant.COLLEGE),
    /**
     * 年级辅导员
     */
    INSTRUCTOR(4, UserTypeConstant.INSTRUCTOR),
    /**
     * 超级管理员
     */
    ADMIN(10, UserTypeConstant.ADMIN),

    /**
     * 班主任
     */
    CLASS_TEACHER(25, UserTypeConstant.CLASS_TEACHER);

    private int type;
    private String name;

    private UserType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * 根据角色名称获取登录用户类型
     *
     * @param roleName 角色名称
     * @return 用户类型
     */
    public static UserType getUserTypeByRole(String roleName) {
        if (StringUtils.isBlank(roleName)) {
            return null;
        }
        for (UserType userType : UserType.values()) {
            if (userType.name.equals(roleName.trim())) {
                return userType;
            }
        }
        return null;
    }
}
