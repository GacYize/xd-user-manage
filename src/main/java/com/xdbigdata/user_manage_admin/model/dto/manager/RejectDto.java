package com.xdbigdata.user_manage_admin.model.dto.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("驳回申请")
public class RejectDto {

    @ApiModelProperty(value = "申请id", required = true)
    @NotNull(message = "申请信息id不能为空")
    private Long id;

    @ApiModelProperty(value = "驳回原因", required = true)
    @NotBlank(message = "驳回意见不能为空")
    @Length(max = 100, message = "驳回意见长度不能超过100个字符")
    private String reason;
}
