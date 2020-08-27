package com.xdbigdata.user_manage_admin.model.vo;

import com.xdbigdata.framework.excel.annotation.DownloadConvert;
import com.xdbigdata.user_manage_admin.util.ExcelConvertUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("学籍异动查询")
@Data
public class XjydVO {

    @ApiModelProperty("学号")
    private String XH;

    @ApiModelProperty("异动类别名称")
    private String YDLBMC;

    @ApiModelProperty("异动日期")
    @DownloadConvert(clazz =ExcelConvertUtils.class,method ="convertOriginDate" )
    private String YDRQ;

    @ApiModelProperty("异动原因")
    private String YDYY;

    @ApiModelProperty("原单位名称")
    private String YDWMC;

    @ApiModelProperty("原专业名称")
    private String YZYMC;

    @ApiModelProperty("原班号")
    private String YBH;

    @ApiModelProperty("原年级")
    private String YNJ;

    @ApiModelProperty("现单位名称")
    private String XDWMC;

    @ApiModelProperty("现专业名称")
    private String XZYMC;

    @ApiModelProperty("现班号")
    private String XBH;

    @ApiModelProperty("现年级")
    private String XNJ;
}
