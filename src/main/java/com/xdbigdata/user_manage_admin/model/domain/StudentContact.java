package com.xdbigdata.user_manage_admin.model.domain;

import com.xdbigdata.user_manage_admin.util.OriginPlaceUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Data
@ApiModel("学生联系信息")
@Table(name = "t_student_contact")
public class StudentContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String sn;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话", required = true)
    @NotBlank(message = "联系电话未填写")
    @Length(max = 12, message = "联系电话长度超出限制")
    private String phone;

    /**
     * 备用电话
     */
    @Column(name = "standby_phone")
    @ApiModelProperty(value = "备用电话", required = true)
    @NotBlank(message = "备用电话未填写")
    @Length(max = 30, message = "备用电话长度超出限制")
    private String standbyPhone;

    /**
     * 微信号
     */
    @ApiModelProperty(value = "微信号", required = true)
    @NotBlank(message = "微信号未填写")
    @Length(max = 50, message = "微信号长度超出限制")
    private String wechat;

    /**
     * qq号
     */
    @ApiModelProperty(value = "qq", required = true)
    @NotBlank(message = "QQ号码未填写")
    @Length(max = 15, message = "QQ号长度超出限制")
    private String qq;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", required = true)
    @NotBlank(message = "邮箱未填写")
    @Email(message = "电子邮箱格式不正确")
    @Length(max = 50, message = "电子邮箱长度超出限制")
    private String email;

    /**
     * 家庭电话
     */
    @ApiModelProperty(value = "家庭电话", required = true)
    @Column(name = "family_phone")
    @NotBlank(message = "家庭电话未填写")
    @Length(max = 12, message = "家庭电话长度超出限制")
    private String familyPhone;

    /**
     * 家庭电子邮箱
     */
    @Column(name = "family_email")
    @ApiModelProperty(value = "家庭电子邮箱", required = true)
    @NotBlank(message = "家庭电子邮箱未填写")
    @Email(message = "家庭电子邮箱格式不正确")
    @Length(max = 50, message = "家庭电子邮箱长度超出限制")
    private String familyEmail;

    /**
     * 家庭所在地区(t_origin_place)冒号分割
     */
    @Column(name = "family_place_code")
    @ApiModelProperty(value = "家庭所在地区编号", required = true)
    @NotBlank(message = "家庭所在地区未选择")
    private String familyPlaceCode;

    /**
     * 获取家庭所在地区
     */
    @ApiModelProperty("家庭所在地区")
    public String getFamilyPlace() {
        return OriginPlaceUtils.getName(this.familyPlaceCode);
    }

    /**
     * 家庭详细地址
     */
    @Column(name = "family_detail_address")
    @ApiModelProperty(value = "家庭详细地址", required = true)
//    @NotBlank(message = "家庭详细地址未填写")
    @Length(max = 30, message = "家庭详细地址不能超过30个字符")
    private String familyDetailAddress;

}