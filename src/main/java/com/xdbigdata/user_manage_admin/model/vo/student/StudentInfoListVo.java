package com.xdbigdata.user_manage_admin.model.vo.student;

import com.xdbigdata.user_manage_admin.util.DictionaryUtil;
import com.xdbigdata.user_manage_admin.util.OrganizationUtil;
import com.xdbigdata.user_manage_admin.util.OriginPlaceUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("学生信息列表")
public class StudentInfoListVo {

    @ApiModelProperty("学号")
    private String sn;

    @ApiModelProperty("姓名")
    private String name;

    private Long gradeId;

    private String collegeCode;

    private String majorCode;

    private String classCode;

    private Long nationId;

    private Long politicsStatusId;

    private Long genderId;

    private String familyPlaceCode;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("辅导员姓名")
    private String instructorName;

    @ApiModelProperty("辅导员电话")
    private String instructorPhone;

    @ApiModelProperty("年级")
    public String getGrade() {
        return OrganizationUtil.getGrade(this.gradeId);
    }

    @ApiModelProperty("学院")
    public String getCollegeName() {
        return OrganizationUtil.getName(this.collegeCode);
    }

    @ApiModelProperty("专业")
    public String getMajorName() {
        return OrganizationUtil.getName(this.majorCode);
    }

    @ApiModelProperty("班级")
    public String getClassName() {
        return OrganizationUtil.getName(this.classCode);
    }

    @ApiModelProperty("民族")
    public String getNation() {
        return DictionaryUtil.getName(this.nationId);
    }

    @ApiModelProperty("政治面貌")
    public String getPoliticsStatus() {
        return DictionaryUtil.getName(this.politicsStatusId);
    }

    @ApiModelProperty("性别")
    public String getGender() {
        return DictionaryUtil.getName(this.genderId);
    }

    @ApiModelProperty("所在地区")
    public String getFamilyPlace() {
        return OriginPlaceUtils.getName(this.familyPlaceCode);
    }
}
