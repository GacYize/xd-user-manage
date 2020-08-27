package com.xdbigdata.user_manage_admin.model.qo.teacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddAndUpdateTeacherQo {

    @ApiModelProperty(value = "用户id", required = false)
    private Long id;
    @ApiModelProperty(value = "头像", required = true)
    private String picUrl;
    @ApiModelProperty(value = "职工号", required = true)
    private String sn;
    @ApiModelProperty(value = "姓名", required = true)
    private String name;
    @ApiModelProperty(value = "性别", required = false)
    private Integer gender;
    @ApiModelProperty(value = "民族", required = false)
    private Long nationId;
    @ApiModelProperty(value = "政治面貌", required = false)
    private Integer politicalStatus;
    @ApiModelProperty(value = "籍贯", required = false)
    private Long nativePlaceId;
    @ApiModelProperty(value = "身份证件类型", required = false)
    private Integer idType;
    @ApiModelProperty(value = "证件号码", required = false)
    private String idNumber;
    @ApiModelProperty(value = "角色 id集合", required = false)
    private List<Long> roleIds;
    @ApiModelProperty(value = "本人电话", required = false)
    private String phone;
    @ApiModelProperty(value = "QQ", required = false)
    private String qq;
    @ApiModelProperty(value = "微信号", required = false)
    private String wechat;
    @ApiModelProperty(value = "邮箱", required = false)
    private String email;
    @ApiModelProperty(value = "校区id", required = false)
    private Long campusId;


}