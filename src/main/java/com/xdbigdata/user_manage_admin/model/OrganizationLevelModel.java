package com.xdbigdata.user_manage_admin.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author caijiang
 * @date 2018/10/28
 */
@Data
@Table(name = "t_organization_level")
public class OrganizationLevelModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "parent_id")
    private Long parentId;

}
