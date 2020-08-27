package com.xdbigdata.user_manage_admin.model.dto.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PunishStudentQo {

    @ApiModelProperty(value = "学生学号")
    private String sn;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "学院id")
    private Long collegeId;

    @ApiModelProperty(value = "专业id")
    private Long majorId;

    @ApiModelProperty(value = "年级名字")
    private String gradeName;

    @ApiModelProperty(value = "违纪角色id")
    private Long illegalRoleId;

    @ApiModelProperty(value = "违纪种类（一级）id")
    private Long illegalTypeId;

    @ApiModelProperty(value = "处分结果id")
    private Long punishResultId;

    @ApiModelProperty(value = "添加时间-开始时间")
    private Long startTime;

    @ApiModelProperty(value = "添加时间-结束时间")
    private Long endTime;

    @ApiModelProperty(value = "是否处分中：1，是；2，否")
    private Integer state;


    private String startDate;

    private String endDate;

    private Long id;

    /**
     * 当前时间+7
     */
    private String date;

    //记录id列表
    private List<Long> ids;

    //学院id列表
    private List<Long> collegeIds;
    //审批状态码
    private Byte appvStatus;
    //是否学生查询，学号
    private String snNew;
    //流程id
    @ApiModelProperty(value = "流程id")
    private Byte flowId;
    //流程是否结束
    private String isFinish;

    @ApiModelProperty("处分状态")
    private String status;

    private List<Byte> appvStatuss;

    @ApiModelProperty("后台自用")
    private Integer oldId;

    @ApiModelProperty("审批，包含三种状态：全部:0,已审批:1,待审批:2")
    private Integer appvStep;

    private List<Integer> appvStatusList;

    private Integer nAppvStatus;
}
