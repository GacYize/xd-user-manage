package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.StudentOrganizationLineModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface StudentOrganizationLineMapper extends TKMapper<StudentOrganizationLineModel> {

	/**
	 * 修改学生的lineId
	 *
	 * @param studentOrganizationLineModel
	 */
	void updateLine(StudentOrganizationLineModel studentOrganizationLineModel);
	/**
	 * 添加到表t_student_organization_line中
	 * @param studentOrganizationLineModel
	 */
	void insertStudentOrganizationLine(StudentOrganizationLineModel studentOrganizationLineModel);

	/**
	 * 删除所有的学生和line id的对应关系
	 */
	void deleteByStudent(@Param("list") List<StudentOrganizationLineModel> studentOrganizationLineModels);

	/**
	 * 删除辅导员和学生关系信息
	 */
    void deleteInstructorStudent();

	/**
	 * 更新辅导员和学生关系信息
	 */
	void updateInstructorStudent();

	/**
	 * 根据学生学号查询自治机构线
	 * @param sn
	 * @return
	 */
	StudentOrganizationLineModel queryLineIdByStudentSn(@Param("sn") String sn);

	/**
	 * 新增学生组织线
	 * @param userId
	 * @param lineId
	 */
	void insertStudentNewLine(@Param("userId") Long userId,@Param("lineId") Long lineId);

	void updateLineIdByStudent(@Param("lineId") Long lineId,@Param("studentId") Long studentId);
}
