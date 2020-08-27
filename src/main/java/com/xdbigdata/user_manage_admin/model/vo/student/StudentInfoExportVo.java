package com.xdbigdata.user_manage_admin.model.vo.student;

import com.xdbigdata.framework.excel.annotation.DownloadConvert;
import com.xdbigdata.framework.excel.annotation.DownloadExcelTitle;
import com.xdbigdata.user_manage_admin.util.ExcelConvertUtils;
import com.xdbigdata.user_manage_admin.util.OriginPlaceUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("学生信息导出")
public class StudentInfoExportVo {

    @DownloadExcelTitle(title = "学号", order = 1)
    @ApiModelProperty("学号")
    private String sn;

    @DownloadExcelTitle(title = "姓名", order = 2)
    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("年级")
    @DownloadExcelTitle(title = "年级", order = 3)
    private String grade;

    @ApiModelProperty("学院")
    @DownloadExcelTitle(title = "学院", order = 4)
    private String collegeName;

    @ApiModelProperty("专业")
    @DownloadExcelTitle(title = "专业", order = 5)
    private String majorName;

    @ApiModelProperty("班级")
    @DownloadExcelTitle(title = "班级", order = 6)
    private String className;

    @ApiModelProperty("民族")
    @DownloadExcelTitle(title = "民族", order = 7)
    private String nation;

    @ApiModelProperty("政治面貌")
    @DownloadExcelTitle(title = "政治面貌", order = 8)
    private String politicsStatus;

    @ApiModelProperty("性别")
    @DownloadExcelTitle(title = "性别", order = 9)
    private String gender;

    @DownloadExcelTitle(title = "所在地区", order = 10)
    @DownloadConvert(clazz = ExcelConvertUtils.class, method = "convertOriginPlace")
    private String familyPlaceCode;

    @ApiModelProperty("所在地区")
    public String getFamilyPlace() {
        return OriginPlaceUtils.getName(this.familyPlaceCode);
    }

    @ApiModelProperty("电话")
    @DownloadExcelTitle(title = "电话", order = 11)
    private String phone;

    @ApiModelProperty("辅导员姓名")
    @DownloadExcelTitle(title = "辅导员姓名", order = 12)
    private String instructorName;

    @ApiModelProperty("辅导员电话")
    @DownloadExcelTitle(title = "辅导员电话", order = 13)
    private String instructorPhone;

}
