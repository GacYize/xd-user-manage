package com.xdbigdata.user_manage_admin.model.vo;

import com.xdbigdata.user_manage_admin.model.domain.StudentModifyAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("学生提交的修改记录详情")
public class SubmitModifyDetailVo {

    @ApiModelProperty("审核表id")
    private StudentModifyAudit studentModifyAudit;

    @ApiModelProperty("入学照片地址")
    private String picUrl;

    @ApiModelProperty("辅导员姓名(年级辅导员)")
    private List<String> instructorName;

    /*********其它系统的信息**********/
    @ApiModelProperty("宿舍号")
    private String dorm;

    @ApiModelProperty("宿舍分布区")
    private String dormitoryArea;

    @ApiModelProperty("班主任")
    private List<String> classTeacher;
}
