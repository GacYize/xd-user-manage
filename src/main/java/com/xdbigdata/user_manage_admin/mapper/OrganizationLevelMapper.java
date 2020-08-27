package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.OrganizationLevelModel;
import com.xdbigdata.user_manage_admin.model.oracle.OrganizationBasic;
import com.xdbigdata.user_manage_admin.model.oracle.OrganizationIds;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author caijiang
 * @date 2018/10/28
 */
public interface OrganizationLevelMapper extends TKMapper<OrganizationLevelModel> {

    /**
     * 新增组织父级关系表
     * @param organizationLevelModel
     */
    void insertOrganizationLevel(OrganizationLevelModel organizationLevelModel);

    /**
     * 查询组织的父级id
     * @param organizationId
     * @return
     */
    Long getParentIdByOrganizationId(@Param("organizationId")Long organizationId);

    /**
     * 根据organizationId删除t_organization_level
     * @param organizationId
     */
    void deleteByOrganizationId(@Param("organizationId")Long organizationId);

    /**
     * 根据组织机构基本信息添加关系信息
     *
     * @param organizationBasics
     */
    void insertListByBasic(List<OrganizationBasic> organizationBasics);


    /**
     * 根据class sn查询不存在的lineId
     *
     * @param classSns 班级编号集合
     * @return
     */
    List<OrganizationIds> selectOrganizationIdsByClass(@Param("list") List<String> classSns);

    /**
     * 查询层级关系
     * @param organizationId
     * @param parentId
     * @return
     */
    List<OrganizationLevelModel> queryLevel(@Param("organizationId") String organizationId,@Param("parentId") String parentId);

    void insertlevel(@Param("organizationId") Long organizationId,@Param("parentId") Long parentId);
}
