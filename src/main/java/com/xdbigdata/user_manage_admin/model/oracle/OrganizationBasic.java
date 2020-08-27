package com.xdbigdata.user_manage_admin.model.oracle;

import com.xdbigdata.user_manage_admin.constant.CategoryConstant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrganizationBasic {

    private String code;
    private String name;
    private Integer type;
    private String parentCode;
    private String parentName;

    public static List<OrganizationBasic> createByStudentBasic(StudentRemote studentBasic) {
        List<OrganizationBasic> result = new ArrayList<>();
        result.add(college(studentBasic));
        result.add(major(studentBasic));
        result.add(clazz(studentBasic));
        return result;
    }

    private static OrganizationBasic college(StudentRemote studentBasic) {
        OrganizationBasic organization = new OrganizationBasic();
        organization.name = studentBasic.getCollege();
        organization.code = studentBasic.getCollegeCode();
        organization.parentName = CategoryConstant.SCHOOL_NAME;
        organization.type = CategoryConstant.CATEGORY_COLLEGE;
        return organization;
    }

    private static OrganizationBasic major(StudentRemote studentBasic) {
        OrganizationBasic organization = new OrganizationBasic();
        organization.name = studentBasic.getMajor();
        organization.code = studentBasic.getMajorCode();
        organization.parentCode = studentBasic.getCollegeCode();
        organization.type = CategoryConstant.CATEGORY_MAJOR;
        return organization;
    }

    private static OrganizationBasic clazz(StudentRemote studentBasic) {
        OrganizationBasic organization = new OrganizationBasic();
        organization.name = studentBasic.getClazz();
        organization.code = studentBasic.getClassCode();
        organization.parentCode = studentBasic.getMajorCode();
        organization.type = CategoryConstant.CATEGORY_CLASS;
        return organization;
    }

}
