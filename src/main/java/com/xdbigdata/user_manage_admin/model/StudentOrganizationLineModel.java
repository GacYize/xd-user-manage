package com.xdbigdata.user_manage_admin.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author caijiang
 * @date 2018/10/28
 */
@Data
@Table(name = "t_student_organization_line")
public class StudentOrganizationLineModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学生用户表中的id
     */
    @Column(name = "student_id")
    private Long studentId;

    /**
     * id,每次增加都找到最大的id,然后+1,保证每条线的唯一性
     */
    @Column(name = "line_id")
    private Long lineId;

	public StudentOrganizationLineModel() {
	}

	public StudentOrganizationLineModel(Long studentId) {
		this.studentId = studentId;
	}

}
