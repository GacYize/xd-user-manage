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
@Table(name = "t_organization_line")
public class OrganizationLineModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 教学机构的id
     */
    @Column(name = "organization_id")
    private Long organizationId;

    /**
     * id,每次增加都找到最大的id,然后+1,保证每条线的唯一性
     */
    @Column(name = "line_id")
    private Long lineId;

	public OrganizationLineModel() {
	}
    
	public OrganizationLineModel(Long organizationId, Long lineId) {
		this.organizationId = organizationId;
		this.lineId = lineId;
	}

}
