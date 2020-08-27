package com.xdbigdata.user_manage_admin.model.qo.student;

import com.xdbigdata.framework.web.model.PageQuery;
import com.xdbigdata.user_manage_admin.model.vo.ManageScope;
import com.xdbigdata.user_manage_admin.model.vo.SortVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@ApiModel("学生列表查询对象-新")
@EqualsAndHashCode(callSuper = false)
public class StudentInfoListQo extends PageQuery {

    private static final String SCHOOL_CODE = "abtc";

    /**
     * user 校区id
     */
    @ApiModelProperty("校区id")
    private Long campusId;
    @ApiModelProperty("身份证号")
    private String idNumber;
    @ApiModelProperty("学号")
    private String sn;
    @ApiModelProperty("姓名")
    private String name;


    /**
     * t_student
     */
    @ApiModelProperty("培养层次")
    private Integer educationLevel;

    @ApiModelProperty("民族 - 数据字典id")
    private List<Long> nationId;

    @ApiModelProperty("政治面貌 - 数据字典id")
    private Long politicalStatusId;

    @ApiModelProperty("性别 - 数据字典id")
    private Long genderId;

    /**
     * student
     */
    @ApiModelProperty("学制")
    private List<String> schoolLength;

    @ApiModelProperty("所在地区编号 - 使用冒号拼接")
    private String familyPlaceCode;

    /**
     * t_student
     */
    @ApiModelProperty("考生号")
    private String candidateNumber;
    @ApiModelProperty("学生状态")
    private List<Long> studentStatus;
    @ApiModelProperty("学生类别")
    private List<Integer> studentType;


    @ApiModelProperty("学院编号集合")
    private List<String> collegeCodeList;

    @ApiModelProperty("专业编号集合")
    private List<String> majorCodeList;

    @ApiModelProperty("年级id集合")
    private List<String> gradeIdList;

    @ApiModelProperty("班级编号集合")
    private List<String> classCodeList;

    @ApiModelProperty("是否上次头像")
    private Integer isHeadPhoto;

    @ApiModelProperty("学籍状态码")
    private Integer educationStatusId;

    @ApiModelProperty("来源省份")
    private Integer sourceProvince;
    @ApiModelProperty("来源市区")
    private Integer sourceCity;
    @ApiModelProperty("关键字:学号,姓名,身份证号,手机号")
    private String keyword;

    @ApiModelProperty("选择显示字段")
    private List<String> displayField;

    @ApiModelProperty("排序对象")
    private SortVo sortVo;

    @ApiModelProperty(hidden = true)
    private List<ManageScope> manageScopes;

    public void setKeyword(String keyword) {
        if (StringUtils.isNotBlank(keyword)) {
            if (keyword.contains("%") || keyword.contains("_")) {
                this.keyword = keyword.trim().replace("%", "[%]").replace("_", "[_]");
            } else {
                this.keyword = keyword.trim();
            }
        }
    }

    public List<ManageScope> getManageScopes() {
        if (CollectionUtils.isEmpty(this.manageScopes)) {
            return null;
        }
        return manageScopes.stream()
                .filter(mc -> !Objects.equals(mc.getOrganizationCode(), SCHOOL_CODE))
                .collect(Collectors.toList());
    }


    public void setAllDisplayField(){
        displayField = new ArrayList<>();
        displayField.add("sn");
        displayField.add("name");
        displayField.add("formername");
        displayField.add("gender_id");
        displayField.add("nation_id");
        displayField.add("politics_status_id");
        displayField.add("native_place_code");
        displayField.add("id_number");
        displayField.add("birth_date");
        displayField.add("health_state_id");
        displayField.add("blood_type_id");
        displayField.add("religion_id");
        displayField.add("current_domicile_place_code");
        displayField.add("departure_destination");
        displayField.add("marital_status_id");
        displayField.add("opening_bank");
        displayField.add("bank_card");
        displayField.add("hobby");
        displayField.add("metro_card");
        displayField.add("stature");
        displayField.add("weight");
        displayField.add("phone");
        displayField.add("standby_phone");
        displayField.add("wechat");
        displayField.add("qq");
        displayField.add("email");
        displayField.add("family_phone");
        displayField.add("family_email");
        displayField.add("family_place");
        displayField.add("family_detail_address");
        displayField.add("college");
        displayField.add("major");
        displayField.add("classes");
        displayField.add("instructor");
        displayField.add("graduate_date");
        displayField.add("source_type");
        displayField.add("education_status_id");
        displayField.add("education_id");
        displayField.add("absentee");
        displayField.add("at_school");
        displayField.add("enter_date");
        displayField.add("domicile_place_code");
        displayField.add("source_place_code");
        displayField.add("high_school");
        displayField.add("sat_scores");
        displayField.add("gradeId");
        displayField.add("instructor_phone");
    }
}
