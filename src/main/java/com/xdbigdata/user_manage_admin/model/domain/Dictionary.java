package com.xdbigdata.user_manage_admin.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "t_dictionary")
public class Dictionary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 数据字典类型(t_dictionary_type)
     */
    @Column(name = "type_id")
    private Long typeId;

    /**
     * 名称
     */
    private String name;

    /**
     * 编号
     */
    private String code;

    @Transient
    private String typeName;

}