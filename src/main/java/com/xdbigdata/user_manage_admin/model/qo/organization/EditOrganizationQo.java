package com.xdbigdata.user_manage_admin.model.qo.organization;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EditOrganizationQo {

    @ApiModelProperty(value = "id", required = true)
    private Long id;
    @ApiModelProperty(value = "名称", required = true)
    private String name;
    @ApiModelProperty(value = "简称", required = true)
    private String sn;
    @ApiModelProperty(value = "组织类型", required = true)
    private Integer type;
    @ApiModelProperty(value = "班级所属年级",required = false)
    private String grade;
    /**
     * 修改人id
     */
    private Long updateUserId;
    /**
     * 修改时间
     */
    private Date updateTime;

}