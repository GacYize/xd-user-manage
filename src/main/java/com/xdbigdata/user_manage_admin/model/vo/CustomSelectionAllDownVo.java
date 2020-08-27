package com.xdbigdata.user_manage_admin.model.vo;

import com.xdbigdata.framework.excel.annotation.DownloadConvert;
import com.xdbigdata.framework.excel.annotation.DownloadExcelTitle;
import com.xdbigdata.user_manage_admin.util.ExcelConvertUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class CustomSelectionAllDownVo {

    /**
     * 学号
     */
    @DownloadExcelTitle(title = "学号", order = 1)
    @ApiModelProperty("学号")
    private String sn;
    /**
     * 姓名
     */
    @DownloadExcelTitle(title = "姓名", order = 2)
    private String name;
    /**
     * 曾用名
     */
    @DownloadExcelTitle(title = "曾用名", order = 3)
    private String formername;

    /**
     * 年级id
     */
    private Long gradeId;

    /**
     * 年级
     */
    @DownloadExcelTitle(title = "年级", order = 4)
    private String grade;

    /**
     * 性别id
     */
    private Long gender_id;

    /**
     * 性别
     */
    @DownloadExcelTitle(title = "性别", order = 8)
    private String gender;

    /**
     * 民族id
     */
    private Long nation_id;

    /**
     * 民族
     */
    @DownloadExcelTitle(title = "民族", order = 9)
    private String nation;

    /**
     * 政治面貌id
     */
    private Long politics_status_id;

    /**
     * 政治面貌
     */
    @DownloadExcelTitle(title = "政治面貌", order = 10)
    private String politicsStatus;

    /**
     * 籍贯code
     */
    private String native_place_code;

    /**
     * 籍贯
     */
    @DownloadExcelTitle(title = "籍贯", order = 11)
    private String nativePlace;


    /**
     * 身份证号
     */
    @DownloadExcelTitle(title = "身份证号", order = 12)
    private String id_number;
    /**
     * 出生日期
     */
    @DownloadExcelTitle(title = "出生日期", order = 13)
    @DownloadConvert(clazz = ExcelConvertUtils.class, method = "convertOriginDate")
    private Date birth_date	;
    /**
     * 健康状况id
     */
    private Long health_state_id;
    /**
     * 健康状况
     */
    @DownloadExcelTitle(title = "健康状况", order = 14)
    private String healthState;

    /**
     * 血型id
     */
    private Long blood_type_id;
    /**
     * 血型
     */
    @DownloadExcelTitle(title = "血型", order = 15)
    private String bloodType;

    /**
     * 宗教信仰id
     */
    private Long religion_id;
    /**
     * 宗教信仰
     */
    @DownloadExcelTitle(title = "宗教信仰", order = 16)
    private String religion;

    /**
     * 现在户口所在地code
     */
    private String current_domicile_place_code;

    /**
     * 现在户口所在地
     */
    @DownloadExcelTitle(title = "现在户口所在地", order = 17)
    private String currentDomicilePlace;

    /**
     * 火车乘车区间
     */
    @DownloadExcelTitle(title = "火车乘车区间", order = 18)
    private String departure_destination;
    /**
     * 婚姻状况id
     */
    private Long marital_status_id;

    /**
     * 婚姻状况
     */
    @DownloadExcelTitle(title = "婚姻状况", order = 19)
    private String maritalStatus;

    /**
     * 与学校联名银行卡账户
     */
    @DownloadExcelTitle(title = "与学校联名银行卡账户", order = 20)
    private String opening_bank;
    /**
     * 银行卡号
     */
    @DownloadExcelTitle(title = "银行卡号", order = 21)
    private String bank_card;
    /**
     * 特长爱好
     */
    @DownloadExcelTitle(title = "特长爱好", order = 22)
    private String hobby;
    /**
     * 校园一卡通卡号
     */
    @DownloadExcelTitle(title = "校园一卡通卡号", order = 23)
    private String metro_card;
    /**
     * 身高(cm)
     */
    @DownloadExcelTitle(title = "身高(cm)", order = 24)
    private Double stature;
    /**
     * 体重(Kg)
     */
    @DownloadExcelTitle(title = "体重(Kg)", order = 25)
    private Double weight;
    /**
     * 联系电话
     */
    @DownloadExcelTitle(title = "联系电话", order = 26)
    private String phone;
    /**
     * 备用电话
     */
    @DownloadExcelTitle(title = "备用电话", order = 27)
    private String standby_phone;
    /**
     * 微信号
     */
    @DownloadExcelTitle(title = "微信号", order = 28)
    private String wechat;
    /**
     * qq号
     */
    @DownloadExcelTitle(title = "qq号", order = 29)
    private String qq;
    /**
     * 邮箱
     */
    @DownloadExcelTitle(title = "邮箱", order = 30)
    private String email;
    /**
     * 家庭电话
     */
    @DownloadExcelTitle(title = "家庭电话", order = 31)
    private String family_phone;
    /**
     * 家庭电子邮箱
     */
    @DownloadExcelTitle(title = "家庭电子邮箱", order = 32)
    private String family_email;
    /**
     * 家庭所在地区code
     */
    private String family_place;

    /**
     * 家庭所在地区
     */
    @DownloadExcelTitle(title = "家庭所在地区", order = 33)
    private String familyPlaceName;

    @ApiModelProperty("辅导员姓名")
    @DownloadExcelTitle(title = "辅导员姓名", order = 34)
    private String instructor;

    @ApiModelProperty("辅导员电话")
    @DownloadExcelTitle(title = "辅导员电话", order = 35)
    private String instructor_phone;

    /**
     * 家庭详细地址
     */
    @DownloadExcelTitle(title = "家庭详细地址", order = 36)
    private String family_detail_address;
    /**
     * 宿舍分布区
     */
    @DownloadExcelTitle(title = "宿舍分布区", order = 37)
    private String roomplace;
    /**
     * 寝室号
     */
    @DownloadExcelTitle(title = "寝室号", order = 38)
    private String roomno;
    /**
     * 学院代码
     */
    private String college;

    /**
     * 学院
     */
    @DownloadExcelTitle(title = "学院", order = 5)
    private String collegeName;

    /**
     * 专业代码
     */
    private String major;

    /**
     * 专业
     */
    @DownloadExcelTitle(title = "专业", order = 6)
    private String majorName;

    /**
     * 班级代码
     */
    private String classes;
    /**
     * 班级
     */
    @DownloadExcelTitle(title = "班级", order = 7)
    private String classesName;

    /**
     * 班主任姓名
     */
    //@DownloadExcelTitle(title = "班主任姓名", order = 39)
    private String class_teacher;
    /**
     * 预计毕业时间
     */
    @DownloadExcelTitle(title = "预计毕业时间", order = 40)
    @DownloadConvert(clazz = ExcelConvertUtils.class, method = "convertOriginDate1")
    private Date graduate_date;
    /**
     * 生源类型
     */
    @DownloadExcelTitle(title = "生源类型", order = 41)
    private String source_type;
    /**
     * 学籍状态id
     */
    private Long education_status_id;

    /**
     * 学籍状态
     */
    @DownloadExcelTitle(title = "学籍状态", order = 42)
    private String educationStatus;

    /**
     * 学生类型id
     */
    private Long education_id;

    /**
     * 学生类型
     */
    @DownloadExcelTitle(title = "学生类型", order = 43)
    private String education;


    /**
     * 是否在籍
     */
//    @DownloadExcelTitle(title = "是否在籍", order = 44)
    private Boolean s_absentee	;

    /**
     * 是否在籍
     */
    @DownloadExcelTitle(title = "是否在籍", order = 44)
    private String absentee	;

    /**
     * 是否在校
     */
//    @DownloadExcelTitle(title = "是否在校", order = 45)
    private Boolean s_at_school;

    /**
     * 是否在校
     */
    @DownloadExcelTitle(title = "是否在校", order = 45)
    private String at_school;
    /**
     * 入学时间
     */
    @DownloadExcelTitle(title = "入学时间", order = 46)
    @DownloadConvert(clazz = ExcelConvertUtils.class, method = "convertOriginDate")
    private Date enter_date;
    /**
     * 入学前户口所在地code
     */
    private String domicile_place_code;

    /**
     * 入学前户口所在地
     */
    @DownloadExcelTitle(title = "入学前户口所在地", order = 47)
    private String domicilePlace;

    /**
     * 生源地code
     */
    private String source_place_code;

    /**
     * 生源地
     */
    @DownloadExcelTitle(title = "生源地", order = 48)
    private String sourcePlace;

    /**
     * 入学前就读中学
     */
    @DownloadExcelTitle(title = "入学前就读中学", order = 49)
    private String high_school	;
    /**
     * 高考分数
     */
    @DownloadExcelTitle(title = "高考分数", order = 50)
    private Double sat_scores;


    public Object gets(String s){
        if("sn".equals(s)){
            return this.sn;
        }else if("name".equals(s)){
            return this.name;
        }else if("formername".equals(s)){
            return this.formername;
        }else if("genderId".equals(s)){
            return this.gender_id;
        }else if("nationId".equals(s)){
            return this.nation_id;
        }else if("native_place_code".equals(s)){
            return this.native_place_code;
        }else if("id_number".equals(s)){
            return this.id_number;
        }else if("birth_date".equals(s)){
            return this.birth_date;
        }else if("politicsStatusId".equals(s)){
            return this.politics_status_id;
        }else if("health_state_id".equals(s)){
            return this.health_state_id;
        }else if("blood_type_id".equals(s)){
            return this.blood_type_id;
        }else if("religion_id".equals(s)){
            return this.religion_id;
        }else if("current_domicile_place_code".equals(s)){
            return this.current_domicile_place_code;
        }else if("departure_destination".equals(s)){
            return this.departure_destination;
        }else if("marital_status_id".equals(s)){
            return this.marital_status_id;
        }else if("opening_bank".equals(s)){
            return this.opening_bank;
        }else if("bank_card".equals(s)){
            return this.bank_card;
        }else if("hobby".equals(s)){
            return this.hobby;
        }else if("stature".equals(s)){
            return this.stature;
        }else if("weight".equals(s)){
            return this.weight;
        }else if("metro_card".equals(s)){
            return this.metro_card;
        }else if("phone".equals(s)){
            return this.phone;
        }else if("standby_phone".equals(s)){
            return this.standby_phone;
        }else if("wechat".equals(s)){
            return this.wechat;
        }else if("qq".equals(s)){
            return this.qq;
        }else if("email".equals(s)){
            return this.email;
        }else if("family_phone".equals(s)){
            return this.family_phone;
        }else if("family_email".equals(s)){
            return this.family_email;
        }else if("family_place".equals(s)){
            return this.family_place;
        }else if("family_detail_address".equals(s)){
            return this.family_detail_address;
        }else if("college".equals(s)){
            return this.college;
        }else if("major".equals(s)){
            return this.major;
        }else if("class".equals(s)){
            return this.classes;
        }else if("graduate_date".equals(s)){
            return this.graduate_date;
        }else if("source_type".equals(s)){
            return this.source_type;
        }else if("education_status_id".equals(s)){
            return this.education_status_id;
        }else if("education_id".equals(s)){
            return this.education_id;
        }else if("absentee".equals(s)){
            return this.absentee;
        }else if("at_school".equals(s)){
            return this.at_school;
        }else if("enter_date".equals(s)){
            return this.enter_date;
        }else if("domicile_place_code".equals(s)){
            return this.domicile_place_code;
        }else if("high_school".equals(s)){
            return this.high_school;
        }else if("sat_scores".equals(s)){
            return this.sat_scores;
        }else if("instructor".equals(s)){
            return this.instructor;
        }else if("instructor_phone".equals(s)){
            return this.instructor_phone;
        }else{
            return null;
        }
    }

}
