package com.xdbigdata.user_manage_admin.model.dto.api;

import com.xdbigdata.framework.excel.annotation.DownloadExcelFirstTitle;
import com.xdbigdata.framework.excel.annotation.DownloadExcelSheet;
import com.xdbigdata.framework.excel.annotation.DownloadExcelTitle;
import com.xdbigdata.framework.excel.annotation.DownloadIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@DownloadExcelSheet(sheetName = "违纪处分列表导出")
@DownloadExcelFirstTitle(name = "违纪处分列表导出")
public class PunishStudentVO {

    @DownloadExcelTitle(title = "学号", order = 1)
    private String sn;

    @DownloadExcelTitle(title = "姓名", order = 2)
    private String studentName;

    @DownloadExcelTitle(title = "学院", order = 3)
    private String collegeName;

    @DownloadExcelTitle(title = "年级", order = 4)
    private String gradeName;

    @DownloadExcelTitle(title = "专业", order = 5)
    private String majorName;

    @DownloadExcelTitle(title = "班级", order = 6)
    private String className;

    @DownloadExcelTitle(title = "违纪角色", order = 7)
    private String illegalRoleName;

    @DownloadExcelTitle(title = "违纪种类", order = 8)
    private String illegalTypeName;

    @DownloadExcelTitle(title = "处分结果", order = 9)
    private String punishResultName;

    @DownloadIgnore
    private Integer punishTerm;

    @DownloadExcelTitle(title = "处分期限（月）", order = 10)
    private String punishTermStr;

    @DownloadExcelTitle(title = "处分添加时间", order = 11)
    private String createDate;
    @DownloadExcelTitle(title = "是否已解除", order = 12)
    private String stateValue;
    @DownloadExcelTitle(title = "处分解除提醒时间", order = 13)
    private String waitCancelDate;
    @DownloadExcelTitle(title = "处分解除时间", order = 14)
    private String updateDate;

    @ApiModelProperty(value = "是否展示为红色")
    @DownloadIgnore
    private boolean flag;

    @ApiModelProperty(value = "解除类型：1，待解除；2，临近解除")
    @DownloadIgnore
    private Integer cancelType;

    @DownloadIgnore
    private Integer state;

    @DownloadIgnore
    private Long classId;

    @DownloadIgnore
    private Long id;

    @DownloadIgnore
    private Long collegeId;

    @DownloadIgnore
    private Long majorId;


    @DownloadIgnore
    private Long illegalRoleId;

    @DownloadIgnore
    private Long illegalTypeId;


    @DownloadIgnore
    private Long illegalTypeLevel2Id;
    @DownloadIgnore
    private String illegalTypeLevel2Name;

    @DownloadIgnore
    private Long punishResultId;

    @DownloadIgnore
    private String doSn;
    @DownloadIgnore
    private String doName;
    @DownloadIgnore
    private String cancelSn;
    @DownloadIgnore
    private String cancelName;

    //当前延长违纪期限的操作人sn
    @DownloadIgnore
    private String extendSn;
    //当前延长违纪期限的操作人名字
    @DownloadIgnore
    private String extendName;
    @DownloadIgnore
    private String punishFileNum;
    @DownloadIgnore
    private String briefIntroduction;

    @ApiModelProperty(value = "计算单位：0-天，1-月")
    private Integer unit;

    @ApiModelProperty(value = "可变的处罚时间")
    private Integer variableTerm;

    @ApiModelProperty(value = "流程审批状态 BYTE")
    private Byte appvStatus;

    @ApiModelProperty(value = "流程审批状态  APPV:审批中  COMP:审批通过 REJT:驳回")
    private String  flowStatus;

    @ApiModelProperty(value = "流程节点id")
    private Byte taskId;

    @ApiModelProperty(value = "流程是否结束 Y:是 N:否")
    private String isFinish;

    @ApiModelProperty(value = "流程节点id")
    private Byte flowId;

    @ApiModelProperty("处分流程id")
    private String oldId;

    @ApiModelProperty("文件列表")
    private String filess;

    public void setCreateDate(String createDate) {
        this.createDate = CommonUtil.formatDateStr(createDate);
    }

    public void setWaitCancelDate(String waitCancelDate) {
        if (CheckParamsUtil.check(waitCancelDate)) {

            this.waitCancelDate = CommonUtil.formatDateStr(waitCancelDate);
        }
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = CommonUtil.formatDateStr(updateDate);
    }
}
