package com.xdbigdata.user_manage_admin.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author caijiang
 * @date 2018/10/25
 */
@Table(name="t_organization")
@Data
public class OrganizationModel implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
     * 是否跨学院
     */
    @Column(name = "across_college")
    private Integer acrossCollege;
    /**
     * 类别 0：学校；1：学院；2：专业；3：大类；4：班级 6:行政单位
     */
    private Integer type;
    /**
     * 年级
     */
    private String grade;
    /**
     * 是否有效 1：有效；0：无效
     */
    private Integer valid;
    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 修改人id
     */
    @Column(name = "update_user_id")
    private Long updateUserId;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 创建人id
     */
    @Column(name = "create_user_id")
    private Long createUserId;

}
