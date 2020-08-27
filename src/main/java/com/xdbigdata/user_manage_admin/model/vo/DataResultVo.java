package com.xdbigdata.user_manage_admin.model.vo;

import lombok.Data;

@Data
public class DataResultVo {
    private String name;
    private String value;
    private String code;

    public DataResultVo() {
    }

    public DataResultVo(String name, String value, String code) {
        this.name = name;
        this.value = value;
        this.code = code;
    }
}
