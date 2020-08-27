package com.xdbigdata.user_manage_admin.model.domain;

import com.xdbigdata.user_manage_admin.util.DictionaryUtil;
import com.xdbigdata.user_manage_admin.util.OriginPlaceUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel("学生基本信息")
@Table(name = "t_student_basic")
public class StudentBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 生活照片(fastdfs中的地址)
     */
    @Column(name = "life_head_url")
    @ApiModelProperty("生活照片地址")
    private String lifeHeadUrl;

    /**
     * 学号
     */
    @ApiModelProperty("学号")
    private String sn;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
//    @NotBlank(message = "姓名未填写")
    @Length(max = 30, message = "姓名长度超出限制")
    private String name;
    /**
     * 年级
     */
    @Column(name = "grade")
    private String grade;

    /**
     * 曾用名
     */
    @ApiModelProperty("曾用名")
    @Column(name = "former_name")
    @Length(max = 30, message = "曾用名长度超出限制")
    private String formerName;

    /**
     * 性别(t_dictionary)
     */
    @Column(name = "gender_id")
    @ApiModelProperty(value = "性别 - 数据字典id", required = true)
//    @NotNull(message = "性别未选择")
    private Long genderId;

    /**
     * 获取性别描述
     */
    @ApiModelProperty("性别")
    public String getGender() {
        return DictionaryUtil.getName(this.genderId);
    }

    /**
     * 民族(t_dictionary)
     */
    @Column(name = "nation_id")
    @ApiModelProperty(value = "民族 - 数据字典id", required = true)
//    @NotNull(message = "民族未选择")
    private Long nationId;

    /**
     * 获取民族描述
     */
    @ApiModelProperty("民族")
    public String getNation() {
        return DictionaryUtil.getName(this.nationId);
    }

    /**
     * 国籍(t_dictionary)
     */
    @Column(name = "nationality_id")
    @ApiModelProperty(value = "国籍 - 数据字典id", required = true)
    @NotNull(message = "国籍未选择")
    private Long nationalityId;

    /**
     * 获取国籍描述
     */
    @ApiModelProperty("国籍")
    public String getNationality() {
        return DictionaryUtil.getName(this.nationalityId);
    }

    /**
     * 籍贯(t_origin_place)冒号分割
     */
    @Column(name = "native_place_code")
    @ApiModelProperty(value = "籍贯编码", required = true)
    @NotNull(message = "籍贯未选择")
    private String nativePlaceCode;

    /**
     * 获取籍贯
     */
    @ApiModelProperty("籍贯")
    public String getNativePlace() {
        return OriginPlaceUtils.getName(this.nativePlaceCode);
    }

    /**
     * 身份证号
     */
    @Column(name = "id_number")
    @ApiModelProperty(value = "身份证号", required = true)
    @NotBlank(message = "身份证未填写")
    @Length(max = 18, message = "身份证号码长度超出限制")
    private String idNumber;

    /**
     * 出生日期
     */
    @Column(name = "birth_date")
    @ApiModelProperty("出生日期")
    private Date birthDate;

    /**
     * 政治面貌(t_dictionary)
     */
    @Column(name = "politics_status_id")
    @ApiModelProperty(value = "政治面貌 - 数据字典id", required = true)
//    @NotNull(message = "政治面貌未选择")
    private Long politicsStatusId;

    /**
     * 获取政治面貌描述
     */
    @ApiModelProperty("政治面貌")
    public String getPoliticsStatus() {
        return DictionaryUtil.getName(this.politicsStatusId);
    }

    /**
     * 血型(t_dictionary)
     */
    @Column(name = "blood_type_id")
    @ApiModelProperty(value = "血型 - 数据字典id", required = true)
    @NotNull(message = "血型未选择")
    private Long bloodTypeId;

    /**
     * 获取血型描述
     */
    @ApiModelProperty("血型")
    public String getBloodType() {
        return DictionaryUtil.getName(this.bloodTypeId);
    }

    /**
     * 宗教信仰(t_dictionary)
     */
    @Column(name = "religion_id")
    @ApiModelProperty(value = "宗教信仰 - 数据字典id", required = true)
//    @NotNull(message = "宗教信仰未选择")
    private Long religionId;

    /**
     * 获取宗教信仰描述
     */
    @ApiModelProperty("宗教信仰")
    public String getReligion() {
        return DictionaryUtil.getName(this.religionId);
    }

    /**
     * 现在户口所在地(t_origin_place)冒号分割
     */
    @Column(name = "current_domicile_place_code")
    @ApiModelProperty(value = "现在户口所在地编号", required = true)
    @NotBlank(message = "现在户口所在地未选择")
    private String currentDomicilePlaceCode;

    /**
     * 获取户口所在地描述
     */
    @ApiModelProperty("现在户口所在地")
    public String getCurrentDomicilePlace() {
        return OriginPlaceUtils.getName(this.currentDomicilePlaceCode);
    }

    /**
     * 出发地(用于购买火车票)
     */
    @Column(name = "departure_place")
    @ApiModelProperty("出发地(用于购买火车票)")
    private String departurePlace;

    /**
     * 目的地(用于购买火车票)
     */
    @Column(name = "destination_place")
    @ApiModelProperty("目的地(用于购买火车票)")
    private String destinationPlace;

    /**
     * 婚姻状况(t_dictionary)
     */
    @Column(name = "marital_status_id")
    @ApiModelProperty(value = "婚姻状况 - 数据字典id", required = true)
    @NotNull(message = "婚姻状况未选择")
    private Long maritalStatusId;

    /**
     * 获取婚姻状况描述
     */
    @ApiModelProperty("婚姻状况")
    public String getMaritalStatus() {
        return DictionaryUtil.getName(this.maritalStatusId);
    }

    /**
     * 与学校联名银行卡账户(开户行)
     */
    @Column(name = "opening_bank")
    @ApiModelProperty(value = "与学校联名银行卡账户(开户行)", required = true)
//    @NotBlank(message = "与学校联名银行卡账户未填写")
    @Length(max = 20, message = "与学校联名银行卡账户长度超过限制")
    private String openingBank;

    /**
     * 银行卡号
     */
    @Column(name = "bank_card")
    @ApiModelProperty(value = "银行卡号", required = true)
//    @NotBlank(message = "银行卡号未填写")
    @Length(max = 20, message = "银行卡号长度超过限制")
    private String bankCard;

    /**
     * 特长爱好
     */
    @ApiModelProperty("特长爱好")
    @Length(max = 200, message = "特长爱好长度超过限制")
    private String hobby;

    /**
     * 健康状况(t_dictionary)
     */
    @Column(name = "health_state_id")
    @ApiModelProperty(value = "健康状况 - 数据字典id", required = true)
    @NotNull(message = "健康状况未选择")
    private Long healthStateId;

    /**
     * 获取健康状况描述
     */
    @ApiModelProperty("健康状况")
    public String getHealthState() {
        return DictionaryUtil.getName(this.healthStateId);
    }

    /**
     * 身高(cm)
     */
    @ApiModelProperty(value = "身高", required = true)
    @NotNull(message = "身高未填写")
    @Range(min = 0, max = 300, message = "身高范围超出限制")
    private Double stature;

    /**
     * 体重(Kg)
     */
    @ApiModelProperty(value = "体重", required = true)
    @NotNull(message = "体重未填写")
    @Range(min = 0, max = 300, message = "体重范围超出限制")
    private Double weight;

    /**
     * 校园一卡通卡号
     */
    @Column(name = "metro_card")
    @ApiModelProperty(value = "校园一卡通卡号", required = true)
    @NotBlank(message = "校园一卡通卡号未填写")
    @Length(max = 20, message = "一卡通号长度超过限制")
    private String metroCard;

    @ApiModelProperty(value = "是否是自费生")
    @Column(name = "is_self_supporting")
    private Integer isSelfSupporting;

    @ApiModelProperty(value = "网登编号")
    @Column(name = "net_number")
    private String netNumber;

    @Column(name = "family_status")
    private String familyStatus;


    /**
     * 考生号
     */
    @Column(name = "candidate_number")
    @ApiModelProperty("考生号")
    private String candidateNumber;


    /**
     * 学历层次
     */
    @Column(name = "education_level")
    @ApiModelProperty("学历层次")
    private String educationLevel;


    /**
     * 学历层次代码
     */
    @Column(name = "education_level_code")
    @ApiModelProperty("学历层次代码")
    private String educationLevelCode;
    /**
     * 学生类别
     */
    @Column(name = "studentType")
    @ApiModelProperty("学生类别")
    private Integer studentType;
    /**
     * 学生类别名称
     */
    @Column(name = "studentTypeName")
    @ApiModelProperty("学生类别名称")
    private String studentTypeName;
    /**
     * 是否单亲
     */
    @Column(name = "is_single_parent")
    @ApiModelProperty("是否单亲")
    private Integer isSingleParent;
    /**
     * 户籍类型
     */
    @ApiModelProperty("户籍类型")
    @Column(name = "census_register_type")
    @NotBlank(message = "户籍类型未填写")
    private Long censusRegisterType;
    @Transient
    private String censusRegisterTypeStr;
    /**
     * 人员类型（医保）t_dictionary表id
     */
    @Column(name = "personnel_type")
    @ApiModelProperty("人员类型")
    @NotBlank(message = "人员类型未填写")
    private Long personnelType;
    @Transient
    private String personnelTypeStr;
    /**
     * 人员类别（医保）t_dictionary表id
     */
    @Column(name = "personnel_status")
    @ApiModelProperty("人员类别")
    @NotBlank(message = "人员类别未填写")
    private Long personnelStatus;
    @Transient
    private String personnelStatusStr;
    /**
     * 生源地
     */
    @Column(name = "source_place")
    private String sourcePlace;
}