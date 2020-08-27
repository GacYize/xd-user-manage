package com.xdbigdata.user_manage_admin.model.qo.student;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author caijiang
 * @date 2018/10/29
 */
@Data
public class AddAndUpdateStudentQo {

    /**
     * ********************************基础信息************************************
     */
    @ApiModelProperty(value = "学生用户id", required = false)
    private Long id;
    @ApiModelProperty(value = "学号", required = true)
    private String sn;
    @ApiModelProperty(value = "姓名", required = true)
    private String name;
    @ApiModelProperty(value = "性别", required = false)
    private Integer gender;
    @ApiModelProperty(value = "民族", required = false)
    private Long nationId;
    @ApiModelProperty(value = "政治面貌", required = false)
    private Long politicalStatus;
    @ApiModelProperty(value = "籍贯", required = false)
    private Long nativePlaceId;
    @ApiModelProperty(value = "身份证件类型", required = false)
    private Integer idType;
    @ApiModelProperty(value = "证件号码", required = false)
    private String idNumber;
    @ApiModelProperty(value = "宗教信仰", required = false)
    private String religion;
    @ApiModelProperty(value = "出生年月", required = false)
    private Date birthDate;
    @ApiModelProperty(value = "饮食习惯", required = false)
    private String dietaryHabit;
    @ApiModelProperty(value = "入学年月", required = false)
    private Date enterDate ;
    @ApiModelProperty(value = "校区id", required = false)
    private Long campusId;
    @ApiModelProperty(value = "毕业年月", required = false)
    private Date graduateDate;
    @ApiModelProperty(value = "特长爱好", required = false)
    private String hobby;
    @ApiModelProperty(value = "宿舍号", required = false)
    private String dorm;
    @ApiModelProperty(value = "头像", required = false)
    private String picUrl;
    /**
     * ************************************所属组织机构***************************************
     */
    @ApiModelProperty(value = "学历层次", required = false)
    private String educationLevel;
    @ApiModelProperty(value = "学制", required = false)
    private String studentLength;
    @ApiModelProperty(value = "所属班级id",required = false)
    private Long classId;
    /**
     * ************************************本人联系信息******************************************
     */
    @ApiModelProperty(value = "本人电话", required = false)
    private String phone;
    @ApiModelProperty(value = "QQ", required = false)
    private String qq;
    @ApiModelProperty(value = "微信号", required = false)
    private String wechat;
    @ApiModelProperty(value = "邮箱", required = false)
    private String email;
    /**
     * **********************************家庭信息**************************************************
     */
    @ApiModelProperty(value = "家庭联系人", required = false)
    private String homeContact;
    @ApiModelProperty(value = "家庭电话", required = false)
    private String homePhone;
    @ApiModelProperty(value = "家庭地址", required = false)
    private String homeAddress;
    @ApiModelProperty(value = "家庭邮编", required = false)
    private String homePostcode;

}
