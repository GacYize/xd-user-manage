package com.xdbigdata.user_manage_admin.model.vo;

import com.xdbigdata.user_manage_admin.model.domain.Dictionary;
import lombok.Data;

import java.util.List;

@Data
public class DictionaryAllVo {

    private String typeName;
    private List<Dictionary> dictionaries;
}
