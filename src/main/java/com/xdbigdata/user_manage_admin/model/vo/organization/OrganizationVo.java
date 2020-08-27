package com.xdbigdata.user_manage_admin.model.vo.organization;

import com.xdbigdata.user_manage_admin.model.vo.TeachersVO;
import lombok.Data;

import java.util.List;

/**
 * @author caijiang
 * @date 2018/10/25
 */
@Data
public class OrganizationVo {

    /**
     * 组织id
     */
    private Long id;
    /**
     * 组织名称
     */
    private String name;
    /**
     * 组织简称
     */
    private String sn;
    /**
     * 组织类别 0：学校；1：学院；2：专业；4：班级
     */
    private Integer type;
    /**
     * 班级所属的年级
     */
    private String grade;
    /**
     * 下属数量 举例：学院则为专业数量；专业则为班级数量；班级则为学生数量
     */
    private Integer num;

    /**
     * 管理教职工
     */
    private List<TeachersVO> teacherVOList;
    /**
     * 下属组织
     */
    private List<OrganizationVo> organizationVoList;

}
