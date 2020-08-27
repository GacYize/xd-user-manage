//package com.xdbigdata.user_manage_admin.model.qo.student;
//
//import com.xdbigdata.framework.web.model.PageQuery;
//import com.xdbigdata.user_manage_admin.model.vo.ManageScope;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import java.util.List;
//
///**
// * @author ghj
// * @Description
// * @date 2019/11/7 15:55
// */
//@Data
//@ApiModel("学生列表多条件查询对象")
//public class StudentQo extends PageQuery {
//
//    @ApiModelProperty("校区id")
//    private Long campusId;
//
//
//    @ApiModelProperty("培养层次")
//    private String educationLevel;
//
//    @ApiModelProperty("民族 - 数据字典id")
//    private List<Long> nationId;
//    @ApiModelProperty("学生类别")
//    private List<String> studentType;
//    @ApiModelProperty("性别 - 数据字典id")
//    private Long genderId;
//    @ApiModelProperty("学生状态")
//    private List<String> studentStatus;
//
//    @ApiModelProperty("学制")
//    private List<String> studentLength;
//
//    @ApiModelProperty("政治面貌 - 数据字典id")
//    private Long politicalStatusId;
//    @ApiModelProperty("来源省份")
//    private Integer sourceProvince;
//    @ApiModelProperty("来源市区")
//    private Integer sourceCity;
//    @ApiModelProperty("考生号")
//    private String candidateNumber;
//
//    @ApiModelProperty("学院编号")
//    private List<String> collegeCode;
//
//    @ApiModelProperty("专业编号")
//    private List<String> majorCode;
//
//    @ApiModelProperty("年级id")
//    private Long gradeId;
//
//    @ApiModelProperty("班级编号")
//    private List<String> classCode;
//    @ApiModelProperty("学籍状态码")
//    private Integer educationStatusId;
//    @ApiModelProperty("身份证号")
//    private String idNumber;
//    @ApiModelProperty("学号")
//    private String sn;
//    @ApiModelProperty("姓名")
//    private String name;
//    @ApiModelProperty("是否上次头像")
//    private Integer isHeadPhoto;
//
//
//    @ApiModelProperty("选择显示字段")
//    private List<String> displayField;
//    @ApiModelProperty(hidden = true)
//    private List<ManageScope> manageScopes;
//    @Override
//    public String toString() {
//        return "StudentQo{" +
//                "campusId=" + campusId +
//                ", educationLevel='" + educationLevel + '\'' +
//                ", nationId=" + nationId +
//                ", studentType=" + studentType +
//                ", genderId=" + genderId +
//                ", studentStatus=" + studentStatus +
//                ", studentLength=" + studentLength +
//                ", politicalStatusId=" + politicalStatusId +
//                ", sourceProvince=" + sourceProvince +
//                ", sourceCity=" + sourceCity +
//                ", candidateNumber='" + candidateNumber + '\'' +
//                ", collegeCode=" + collegeCode +
//                ", majorCode=" + majorCode +
//                ", gradeId=" + gradeId +
//                ", classCode=" + classCode +
//                ", educationStatusId=" + educationStatusId +
//                ", idNumber='" + idNumber + '\'' +
//                ", sn='" + sn + '\'' +
//                ", name='" + name + '\'' +
//                ", isHeadPhoto=" + isHeadPhoto +
//                '}';
//    }
//}
