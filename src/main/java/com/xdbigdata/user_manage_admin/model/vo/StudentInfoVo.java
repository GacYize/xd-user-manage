package com.xdbigdata.user_manage_admin.model.vo;

import com.xdbigdata.user_manage_admin.model.domain.StudentBasic;
import com.xdbigdata.user_manage_admin.model.domain.StudentContact;
import com.xdbigdata.user_manage_admin.model.domain.StudentEducation;
import com.xdbigdata.user_manage_admin.model.domain.StudentFamily;
import com.xdbigdata.user_manage_admin.model.dto.student.PunishStudentDetailVo;
import com.xdbigdata.user_manage_admin.model.dto.student.StudentScholarship;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
 * @author ghj
 * @date 2019/2/18
 */
@Data
@ApiModel("学生个人信息")
public class StudentInfoVo {

    @ApiModelProperty("学生基本信息")
    private StudentBasic studentBasic;

    @ApiModelProperty("学生学籍信息")
    private StudentEducation studentEducation;

    @ApiModelProperty("学生联系信息")
    private StudentContact studentContact;

    @ApiModelProperty("学生家庭信息")
    private List<StudentFamily> studentFamilies;

    @ApiModelProperty("修改状态: 0:可以修改, 1:不能修改")
    private Integer modifyStatus;

    @ApiModelProperty("入学照片地址")
    private String picUrl;

    @ApiModelProperty("辅导员姓名(年级辅导员)")
    private List<String> instructorName;

    /*********其它系统的信息**********/
    @ApiModelProperty("宿舍号")
    private String dorm;

    @ApiModelProperty("宿舍分布区")
    private String dormitoryArea;

    @ApiModelProperty("班主任")
    private List<String> classTeacher;

    @ApiModelProperty("处分信息")
    private List<PunishStudentDetailVo> punishStudentDetailVo;

    @ApiModelProperty("奖励信息")
    private List<StudentScholarship> studentScholarships;

    public StudentBasic getStudentBasic() {
        return Objects.isNull(this.studentBasic) ? new StudentBasic(): this.studentBasic;
    }

    public StudentEducation getStudentEducation() {
        return Objects.isNull(this.studentEducation) ? new StudentEducation(): this.studentEducation;
    }

    public StudentContact getStudentContact() {
        return Objects.isNull(this.studentContact) ? new StudentContact(): this.studentContact;
    }
}
