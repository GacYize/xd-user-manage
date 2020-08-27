package com.xdbigdata.user_manage_admin.service;


import com.xdbigdata.user_manage_admin.model.OrganizationModel;
import com.xdbigdata.user_manage_admin.model.dto.manager.InstructorManageStuInfDto;
import com.xdbigdata.user_manage_admin.model.qo.organization.AddOrganizationQo;
import com.xdbigdata.user_manage_admin.model.qo.organization.EditOrganizationQo;
import com.xdbigdata.user_manage_admin.model.vo.organization.OrganizationVo;

import java.util.List;
import java.util.Set;

/**
 * @author caijiang
 * @date 2018/10/25
 */
public interface OrganizationService{

    /**
     * 获取学校及所属学院信息
     * @return
     */
    OrganizationVo  getSchoolOrganization();

    /**
     * 根据组织id和类型获取下属组织信息
     * @param id
     * @param type
     * @return
     */
    OrganizationVo getOrganization(Long id,Integer type);

    /**
     * 编辑组织的名称、简称
     * @param editOrganizationQo
     * @return
     */
    void update(EditOrganizationQo editOrganizationQo);

    /**
     * 删除组织
     * @param id
     */
    void delete(Long id);

    /**
     * 新建组织机构
     * @param addOrganizationQo
     */
    OrganizationModel add(AddOrganizationQo addOrganizationQo);

    /**
     * 查询所有学院
     * 
     * @return
     */
	List<OrganizationModel> getAllColleges();

    /**
     *获取学院及学校
     * @return
     */
    List<OrganizationModel> getManageColleges();

    /**
     * 根据上级组织id和类型查询下级组织
     * 
     * @param parentId 上级组织id
     * @param type 下级组织类型
     * @return
     */
	List<OrganizationModel> getByParentAndType(Long parentId, Integer type);

	/**
	 * 根据学院id查询所有年级
	 * 
	 * @param collegeId 学院id
	 * @return
	 */
	List<String> getGradesByCollege(Long collegeId);

    /**
     * 根据专业和年级获取班级
     * @param majorId
     * @param grade
     * @return
     */
    List<OrganizationModel> getClassesByMajorAndGrade(Long majorId,String grade);

    /**
     * Description 查询辅导员管辖范围
     * Date 2019/8/13 10:12
     * Param [sn]
     * return java.util.List<com.xdbigdata.user_manage_admin.model.dto.manager.InstructorManageStuInfDto>
     **/
    List<InstructorManageStuInfDto> listInstructorManageStuInfDto(String sn);

    /**
     * 根据多个父级id获取子集
     * @param colleges
     * @param type
     * @return
     */
    List<OrganizationModel> getByParentAndTypeList(List<String> colleges, Integer type);

    /**
     * 根据专业集合和年级集合获取班级
     * @param majorIdList
     * @param gradeList
     * @return
     */
    List<OrganizationModel> getClassesByMajorAndGradeList(List<String> majorIdList, List<String> gradeList);

    List<OrganizationModel> getByParentListAndType(List<Long> majorIdList, Integer categoryClass);
}