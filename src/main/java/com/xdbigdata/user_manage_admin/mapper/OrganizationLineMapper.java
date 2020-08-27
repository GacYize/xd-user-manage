package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.OrganizationLineModel;
import com.xdbigdata.user_manage_admin.model.OrganizationModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author caijiang
 * @date 2018/10/28
 */
public interface OrganizationLineMapper extends TKMapper<OrganizationLineModel> {

    /**
     * 获取当前数据库中存在的最大的line_id
     * @return
     */
    Long getMaxLineId();

    /**
     * 批量添加到t_organization_line中
     * @param organizationLineModelList
     */
    void insertOrganizationLineList(@Param("organizationLineModelList") List<OrganizationLineModel> organizationLineModelList);

    /**
     * 根据班级id查询lineId
     *
     * @param clazzId 班级id
     * @return
     */
	Long selectLineIdByClazz(Long clazzId);

    /**
     * 根据lineId删除t_organization_line
     * @param lineId
     */
    void deleteByLineId(@Param("lineId")Long lineId);


    List<OrganizationModel> selectAllClassLine();

    /**
     * 根据学院、专业、班级获取是否存在该条组织线
     * @param college
     * @param major
     * @param classes
     * @return
     */
    Long queryLine(@Param("college") String college,@Param("major") String major,@Param("classes") String classes);

    void insertNewLine(@Param("organizationId") Long organizationId,@Param("lineId") Long lineId);
}
