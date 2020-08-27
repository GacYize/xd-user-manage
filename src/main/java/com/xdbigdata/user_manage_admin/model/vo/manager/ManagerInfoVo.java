package com.xdbigdata.user_manage_admin.model.vo.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("管理者个人信息")
public class ManagerInfoVo {

    /**
     * 学号
     */
    @ApiModelProperty("工号")
    private String sn;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    @Length(max = 30, message = "姓名长度不能超过30个字符")
    private String name;
    /**
     * 头像路径
     */
    @ApiModelProperty("头像路径")
    @NotBlank(message = "头像路径不能为空")
    private String picUrl;
    /**
     *
     */
    @ApiModelProperty("性别")
    @NotNull(message = "性别不能为空")
    private Integer gender;
    /**
     * 民族id
     */
    @ApiModelProperty("民族id")
    @NotNull(message = "民族id不能为空")
    private Long nationId;

    @ApiModelProperty("民族")
    private String nation;
    /**
     * 政治面貌id
     */
    @ApiModelProperty("政治面貌id")
    @NotNull(message = "政治面貌id不能为空")
    private Integer politicalStatus;

    @ApiModelProperty("政治面貌")
    private String politicalStatusName;

    /**
     * 籍贯编码
     */
    @ApiModelProperty("籍贯编码")
    @NotBlank(message = "籍贯编码不能为空")
    private String originCode;
    /**
     * 籍贯
     */
    @ApiModelProperty("籍贯")
    private String originCodeName;
    /**
     * 校区id
     */
    @ApiModelProperty("校区id")
    @NotNull(message = "校区id不能为空")
    private Long campusId;
    /**
     * 校区
     */
    @ApiModelProperty("校区")
    private String campus;
    /**
     * 身份证号
     */
    @ApiModelProperty("身份证号")
    @NotBlank(message = "身份证号不能为空")
    @Length(max = 30, message = "身份证号长度不能超过30个字符")
    private String idNumber;
    /**
     * 电话
     */
    @ApiModelProperty("电话")
    @NotBlank(message = "电话不能为空")
    @Length(max = 30, message = "电话长度不能超过30个字符")
    private String phone;
    /**
     * 微信
     */
    @ApiModelProperty("微信")
    @NotBlank(message = "微信不能为空")
    @Length(max = 30, message = "微信不能超过30个字符")
    private String wechat;
    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Length(max = 30, message = "邮箱不能超过30个字符")
    private String email;
    /**
     * qq
     */
    @ApiModelProperty("qq")
    @NotBlank(message = "qq不能为空")
    @Length(max = 30, message = "qq不能超过30个字符")
    private String qq;

    @ApiModelProperty("籍贯")
    private String jg;
}
