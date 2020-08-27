package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.OrganizationModel;
import com.xdbigdata.user_manage_admin.model.dto.manager.InstructorManageStuInfDto;
import com.xdbigdata.user_manage_admin.model.qo.organization.EditOrganizationQo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author caijiang
 * @date 2018/10/25
 */
public interface OrganizationMapper extends TKMapper<OrganizationModel> {

    /**
     * 根据组织类型、父级id获取组织
     * @param type
     * @param parentId
     * @return
     */
    List<OrganizationModel> findByTypeAndParentIdAndGrade(@Param("type")Integer type,@Param("parentId")Long parentId,@Param("grade")String grade);

    /**
     * 根据组织类型、父级id获取组织
     * @param type
     * @param parentId
     * @return
     */
    List<OrganizationModel> findByTypeAndParentIdAndSn(@Param("type")Integer type,@Param("parentId")Long parentId,@Param("grade")String grade,@Param("sn") String sn);


    /**
     * 统计组织数量
     * @param type
     * @param parentId
     * @return
     */
    Integer countByTypeAndParentId(@Param("type")Integer type,@Param("parentId")Long parentId);

    /**
     * 根据id获取组织
     * @param id
     * @return
     */
    OrganizationModel findById(@Param("id")Long id);

    /**
     * 修改组织中的名称name和简称sn
     * @param editOrganizationQo
     */
    void updateOrganization(EditOrganizationQo editOrganizationQo);

    /**
     * 根据id删除组织
     * @param id
     */
    void deleteById(@Param("id")Long id);

    /**
     * 统计班级下的学生人数
     * @param classId
     * @return
     */
    Integer countStudentNumByClass(@Param("classId")Long classId);

    /**
     * 新增组织
     * @param organizationModel
     */
    void insertOrganization(OrganizationModel organizationModel);

    /**
     * 根据学生用户id获取学生所属组织机构
     * @param userId
     * @return
     */
    List<OrganizationModel> findByUserId(@Param("userId")Long userId);

    /**
     * 根据上级组织id和类型查询下级组织
     *
     * @param parentId 上级组织id
     * @param type 下级组织类型
     * @return
     */
    List<OrganizationModel> selectByParentAndType(@Param("parentId") Long parentId, @Param("type") Integer type,@Param("grade")String grade);
    
	/**
	 * 根据学院id查询所有年级
	 * 
	 * @param collegeId 学院id
	 * @return
	 */
	List<String> selectGradesByCollege(Long collegeId);

    /**
     * 根据工号获取所属学院
     */
    List<OrganizationModel> queryOrganizationBySn(@Param("sn") String sn,@Param("roleId") Long roleId);

    OrganizationModel getOrganizationData(@Param("sn") String sn);

    List<InstructorManageStuInfDto> selectInstructorManageInf(@Param("sn") String sn);

    /**
     * 根据多个父级id获取子集
     * @param parentCodeList
     * @param type
     * @param gradeList
     * @return
     */
    List<OrganizationModel> selectByParentAndTypeList(@Param("parentCodeList") List<String> parentCodeList,
                                                      @Param("type") Integer type,
                                                      @Param("gradeList")List<String> gradeList);

    List<OrganizationModel> selectByParentListAndType(@Param("parentIdList") List<Long> parentIdList, @Param("type") Integer type,@Param("grade")String grade);
}


