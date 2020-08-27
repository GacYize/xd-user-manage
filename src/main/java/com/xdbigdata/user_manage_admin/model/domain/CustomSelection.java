package com.xdbigdata.user_manage_admin.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "t_custom_selection")
public class CustomSelection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 字段名称
     */
    @Column(name = "field_name")
    private String fieldName;

    /**
     * 显示名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 所属表
     */
    @Column(name = "belong_table")
    private String belongTable;

    /**
     * 排序
     */
    @Column(name = "orders")
    private String orders;

}