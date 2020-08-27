package com.xdbigdata.user_manage_admin.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BedInfoDto {
    @ApiModelProperty("床位id")
    private Long id;
    @ApiModelProperty("入住学生sn")
    private String sn;
    @ApiModelProperty("房间id")
    private Long roomId;
    @ApiModelProperty("房间详细信息")
    private String roomDetailInfo;
}
