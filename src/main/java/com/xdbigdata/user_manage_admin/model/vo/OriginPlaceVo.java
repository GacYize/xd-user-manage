package com.xdbigdata.user_manage_admin.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class OriginPlaceVo {

    private String value;
    private String label;
    private String parentCode;
    private List<OriginPlaceVo> children;
}
