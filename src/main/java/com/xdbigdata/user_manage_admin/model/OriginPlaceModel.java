package com.xdbigdata.user_manage_admin.model;

import lombok.Data;

import javax.persistence.*;

/**
 * 行政区
 */
@Data
@Table(name="t_origin_place")
public class OriginPlaceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "parent_code")
    private String parentCode;


}