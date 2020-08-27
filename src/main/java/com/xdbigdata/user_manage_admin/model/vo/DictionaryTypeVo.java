package com.xdbigdata.user_manage_admin.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author ghj
 * @Description
 * @date 2019/11/7 18:17
 */
@Data
@ApiModel("字典类型下拉框")
public class DictionaryTypeVo {

    private List<DictionaryBaseVo> campusList;

    private List<DictionaryBaseVo> educationLevelList;

    private List<DictionaryBaseVo> politicsStatusList;

    private List<DictionaryBaseVo> studentTypeList;

    private List<DictionaryBaseVo> schoolLengthList;

    private List<DictionaryBaseVo> educationStatusList;
}
