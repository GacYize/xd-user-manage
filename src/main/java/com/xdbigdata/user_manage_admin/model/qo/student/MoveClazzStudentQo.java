package com.xdbigdata.user_manage_admin.model.qo.student;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("学生移动目标对象")
public class MoveClazzStudentQo {

    @ApiModelProperty(value = "学院id", required = true)
    @NotNull(message = "学院id不能为空")
    private Long collegeId;
    @ApiModelProperty(value = "专业id", required = true)
    @NotNull(message = "专业id不能为空")
    private Long majorId;
    @ApiModelProperty(value = "班级id", required = true)
    @NotNull(message = "班级id不能为空")
    private Long clazzId;
    @ApiModelProperty(value = "学生id", required = true)
    @NotNull(message = "学生id不能为空")
    private Long studentId;
}