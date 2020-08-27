package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.dto.UserManagementDto;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerDto;
import com.xdbigdata.user_manage_admin.model.qo.manager.ListManagerQo;
import com.xdbigdata.user_manage_admin.model.vo.QuzzyQueryVO;
import com.xdbigdata.user_manage_admin.model.vo.TeachersVO;
import com.xdbigdata.user_manage_admin.model.vo.manager.BasicInfoManagerVo;
import com.xdbigdata.user_manage_admin.model.vo.manager.ManagerInfoVo;
import com.xdbigdata.user_manage_admin.model.vo.teacher.InstructorNameVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends TKMapper<UserModel> {


    UserModel selectManager(@Param("id") Long id);

    /**
     * 根据sn获取用户
     *
     * @param sn
     * @return
     */
    UserModel selectUserBySn(@Param("sn") String sn);

    /**
     * 管理人员列表查询
     *
     * @param listManagerQo
     * @return
     */
    List<ManagerDto> selectManagers(@Param("query") ListManagerQo listManagerQo, @Param("studentRoleId") Long studentRoleId);

    /**
     * 查询管理人员详情
     *
     * @param managerId
     * @param studentRoleId
     * @return
     */
    BasicInfoManagerVo selectManagerDetail(@Param("managerId") Long managerId, @Param("studentRoleId") Long studentRoleId);

    /**
     * 根据sn和roleName获取用户
     *
     * @param sn
     * @param roleName
     * @return
     */
    UserModel findBySnAndRole(@Param("sn") String sn, @Param("roleName") String roleName);


    /**
     * 批量添加用户
     *
     * @param userModelList
     */
    void addUserList(@Param("userModelList") List<UserModel> userModelList);

    /**
     * 根据sns获取用户id和sn信息
     *
     * @param sns
     * @return
     */
    List<UserModel> findIdAndSnBySnS(@Param("sns") List<String> sns, @Param("roleIds") List<Long> roleIds);

    /**
     * 根据sn获取用户
     *
     * @param sn
     * @return
     */
    ManagerInfoVo selectUserBySn2(@Param("sn") String sn);

    /**
     * 根据sn获取用户
     *
     * @param collegeId
     * @return
     */
    ManagerInfoVo selectUserByCollege(@Param("collegeId") Long collegeId);

    /**
     * 查询用户id
     *
     * @param sn     用户工号
     * @param roleId 登录角色id
     * @return 用户id
     */
    Long selectUserId(@Param("sn") String sn, @Param("roleId") Long roleId);

    /**
     * 根据用户id和角色id查询用户管辖访问
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return 管辖范围
     */
    List<UserManagementDto> selectUserManagement(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 根据年级和组织机构id查询管辖范围
     *
     * @param orgScopes
     * @return sn集合
     */
    List<String> selectSnByOrgs(List<UserManagementDto> orgScopes);

    /**
     * 根据id查询sn
     *
     * @param ids id集合
     * @return sn集合
     */
    List<String> selectSnByIds(List<Long> ids);

    /**
     * 根据组织机构Id获取对应的教职工
     *
     * @param organizationId
     * @param sn
     * @return
     */
    List<TeachersVO> getInstructor(@Param("organizationId") Long organizationId, @Param("sn") String sn);

    /**
     * 模糊匹配教师明细
     *
     * @param quzzyQueryVO
     * @return
     */
    List<TeachersVO> fuzzyQueryUser(QuzzyQueryVO quzzyQueryVO);

    /**
     * 为班级添加辅导员
     */
    void addInstructor(TeachersVO teachersVO);

    /**
     * 移除辅导员
     */
    void deleteInstructor(TeachersVO teachersVO);

    /**
     * 查询重复
     */
    Integer repeatInstructor(TeachersVO teachersVO);

    /**
     * 根据班级code查询对应学生
     */
    List<InstructorNameVO> queryInstructorByCode(@Param("calassCode") String calassCode);

    /**
     * 根据sn集合查询
     * @param snList
     * @return
     */
    List<UserModel> findBySnList(@Param("snList") List<String> snList);

    /**
     * 查询班主任
     * @param sn
     * @return
     */
    List<String> selectClassTeacherBySn(@Param("sn") String sn);

    /**
     * 查询年级辅导员
     * @param sn
     * @return
     */
    List<String> selectInstructorBySn(@Param("sn") String sn);
}
