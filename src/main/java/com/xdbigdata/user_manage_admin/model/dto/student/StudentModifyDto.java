package com.xdbigdata.user_manage_admin.model.dto.student;

import com.xdbigdata.user_manage_admin.model.domain.StudentBasic;
import com.xdbigdata.user_manage_admin.model.domain.StudentContact;
import com.xdbigdata.user_manage_admin.model.domain.StudentEducation;
import com.xdbigdata.user_manage_admin.model.domain.StudentFamily;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@ApiModel("学生修改的信息")
public class StudentModifyDto {

    @Valid
    @ApiModelProperty("学生基本信息")
    @NotNull(message = "学生基本信息不能为空")
    private StudentBasic studentBasic;

    @Valid
    @ApiModelProperty("学生学籍信息")
    @NotNull(message = "学生学籍信息不能为空")
    private StudentEducation studentEducation;

    @Valid
    @ApiModelProperty("学生联系信息")
    @NotNull(message = "学生联系信息不能为空")
    private StudentContact studentContact;

    @Valid
    @ApiModelProperty("学生家庭信息")
    @NotEmpty(message = "学生家庭信息不能为空")
    @Size(max = 10, message = "家庭信息不能超过10条")
    private List<StudentFamily> studentFamilies;
}
