package com.xdbigdata.user_manage_admin.model.qo.organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author caijiang
 * @date 2018/10/26
 */
@Data
public class AddOrganizationQo {

    @ApiModelProperty(value = "名称", required = true)
    private String name;
    @ApiModelProperty(value = "简称")
    private String sn;
    @ApiModelProperty(value = "父节点id")
    private Long parentId;
    @ApiModelProperty(value = "所属年级，班级特有属性")
    private String grade;
    @ApiModelProperty(value = "组织类型")
    private Integer type;

}
