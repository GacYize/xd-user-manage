package com.xdbigdata.user_manage_admin.service.impl;

import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.user_manage.dto.RoleAndInfoDto;
import com.xdbigdata.user_manage_admin.annotation.OperateLog;
import com.xdbigdata.user_manage_admin.constant.CategoryConstant;
import com.xdbigdata.user_manage_admin.constant.UserTypeConstant;
import com.xdbigdata.user_manage_admin.mapper.OrganizationLevelMapper;
import com.xdbigdata.user_manage_admin.mapper.OrganizationLineMapper;
import com.xdbigdata.user_manage_admin.mapper.OrganizationMapper;
import com.xdbigdata.user_manage_admin.mapper.UserMapper;
import com.xdbigdata.user_manage_admin.model.OrganizationLevelModel;
import com.xdbigdata.user_manage_admin.model.OrganizationLineModel;
import com.xdbigdata.user_manage_admin.model.OrganizationModel;
import com.xdbigdata.user_manage_admin.model.dto.manager.InstructorManageStuInfDto;
import com.xdbigdata.user_manage_admin.model.qo.organization.AddOrganizationQo;
import com.xdbigdata.user_manage_admin.model.qo.organization.EditOrganizationQo;
import com.xdbigdata.user_manage_admin.model.vo.TeachersVO;
import com.xdbigdata.user_manage_admin.service.OrganizationService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.model.vo.organization.OrganizationVo;
import com.xdbigdata.user_manage_admin.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author caijiang
 * @date 2018/10/25
 */
@Service
@Slf4j
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationLevelMapper organizationLevelMapper;

    @Autowired
    private OrganizationLineMapper organizationLineMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public OrganizationVo getSchoolOrganization() {
        RoleAndInfoDto session = SessionUtil.getSession();
        //获取学校
        List<OrganizationModel> organizationModelListSchool = organizationMapper.findByTypeAndParentIdAndGrade(0, null, null);
        if (organizationModelListSchool != null && organizationModelListSchool.size() > 0) {
            OrganizationVo organizationVo = new OrganizationVo();
            OrganizationModel school = organizationModelListSchool.get(0);
            BeanUtils.copyProperties(school, organizationVo);
            //获取学校下一级学院信息
            List<OrganizationModel> organizationModelListCollege = null;
            if (Objects.equals(session.getRoleList().get(0).getName(), UserTypeConstant.COLLEGE)) {
                organizationModelListCollege = organizationMapper.findByTypeAndParentIdAndSn(1, school.getId(), null, session.getSn());
            } else {
                organizationModelListCollege = organizationMapper.findByTypeAndParentIdAndGrade(1, school.getId(), null);
            }

            if (organizationModelListCollege != null && organizationModelListCollege.size() > 0) {
                List<OrganizationVo> organizationVoList = new ArrayList<>();
                for (OrganizationModel college : organizationModelListCollege) {
                    OrganizationVo organizationVo1 = new OrganizationVo();
                    BeanUtils.copyProperties(college, organizationVo1);
                    //获取学院包含专业个数
                    Integer num = organizationMapper.countByTypeAndParentId(2, college.getId());
                    organizationVo1.setNum(num);
                    organizationVoList.add(organizationVo1);
                }
                organizationVo.setOrganizationVoList(organizationVoList);
            }
            return organizationVo;
        }
        return null;
    }

    @Override
    public OrganizationVo getOrganization(Long id, Integer type) {
        OrganizationVo organizationVo = new OrganizationVo();
        RoleAndInfoDto session = SessionUtil.getSession();
        //获取当前组织
        OrganizationModel organizationModel = organizationMapper.findById(id);
        BeanUtils.copyProperties(organizationModel, organizationVo);
        if (Objects.equals(type, 1)) {
            //获取学院下一级专业信息
            List<OrganizationModel> organizationModelListMajor = organizationMapper.findByTypeAndParentIdAndGrade(2, id, null);
            if (organizationModelListMajor != null && organizationModelListMajor.size() > 0) {
                List<OrganizationVo> organizationVoList = new ArrayList<>();
                for (OrganizationModel major : organizationModelListMajor) {
                    OrganizationVo organizationVo1 = new OrganizationVo();
                    BeanUtils.copyProperties(major, organizationVo1);
                    //获取专业包含班级个数
                    Integer num = organizationMapper.countByTypeAndParentId(4, major.getId());
                    organizationVo1.setNum(num);
                    organizationVoList.add(organizationVo1);
                }
                organizationVo.setOrganizationVoList(organizationVoList);
            }
        } else if (Objects.equals(type, 2)) {
            //获取专业下一级班级的信息
            List<OrganizationModel> organizationModelListClass = organizationMapper.findByTypeAndParentIdAndGrade(4, id, null);
            if (organizationModelListClass != null && organizationModelListClass.size() > 0) {
                List<OrganizationVo> organizationVoList = new ArrayList<>();
                for (OrganizationModel clazz : organizationModelListClass) {
                    OrganizationVo organizationVo1 = new OrganizationVo();
                    BeanUtils.copyProperties(clazz, organizationVo1);
                    //获取班级下学生人数
                    Integer num = organizationMapper.countStudentNumByClass(clazz.getId());
                    organizationVo1.setNum(num);
                    //管理教职工
                    List<TeachersVO> teacherVOList = userMapper.getInstructor(clazz.getId(), null);
                    if (teacherVOList != null && teacherVOList.size() > 0) {
                        organizationVo1.setTeacherVOList(teacherVOList);
                    }
                    organizationVoList.add(organizationVo1);

                }
                organizationVo.setOrganizationVoList(organizationVoList);
            }
        }
        return organizationVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLog(module = "人员管理")
    public void update(EditOrganizationQo editOrganizationQo) {
        //获取同等类型的组织
        List<OrganizationModel> organizationModelList = organizationMapper.findByTypeAndParentIdAndGrade(editOrganizationQo.getType(), null, null);
        //获取当前要编辑的组织
        OrganizationModel organizationModel = organizationMapper.findById(editOrganizationQo.getId());
        //同类组织集合中删除当前组织，验证不可修改为相同的name和sn
        organizationModelList.remove(organizationModel);
        repeatNameOrSn(organizationModelList, editOrganizationQo.getName(), editOrganizationQo.getSn());
        //编辑组织的名称、简称
        editOrganizationQo.setUpdateUserId(ContextUtil.getId());
        editOrganizationQo.setUpdateTime(new Date());
        organizationMapper.updateOrganization(editOrganizationQo);
        ContextUtil.LOG_THREADLOCAL.set("修改组织" + editOrganizationQo.getName());
    }

    /**
     * 验证新建修改组织的时候name和sn字段不能重名
     */
    private void repeatNameOrSn(List<OrganizationModel> organizationModelList, String name, String sn) {
        if (organizationModelList != null && organizationModelList.size() > 0) {
            List<String> organizationNameString = new ArrayList<>();
            List<String> organizationSnString = new ArrayList<>();
            for (OrganizationModel organizationModel1 : organizationModelList) {
                organizationNameString.add(organizationModel1.getName());
                organizationSnString.add(organizationModel1.getSn());
            }
            if (organizationNameString.contains(name)) {
                throw new BaseException("修改的名称重复，不能修改");
            }
            if (sn != null && !Objects.equals(sn.trim(), "") && organizationSnString.contains(sn)) {
                throw new BaseException("修改的简称重复，不能修改");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLog(module = "人员管理")
    public void delete(Long id) {
        //查找当前需要删除的数据
        OrganizationModel organizationModel = organizationMapper.findById(id);
        if (organizationModel == null) {
            throw new BaseException("当前删除的数据不存在，删除失败");
        }
        //验证：如果当前是班级，查询当前班级下是否有学生，不是班级，则查询下属组织下是否有组织
        if (!Objects.equals(organizationModel.getType(), 4)) {
            Integer organizationNum = organizationMapper.countByTypeAndParentId(null, organizationModel.getId());
            if (!Objects.equals(organizationNum, 0)) {
                throw new BaseException("当前包含下属组织，不能删除");
            }
        } else {
            Integer studentNum = organizationMapper.countStudentNumByClass(id);
            if (!Objects.equals(studentNum, 0)) {
                throw new BaseException("当前班级含有学生，不能删除");
            }
        }
        //删除表t_organization
        organizationMapper.deleteById(id);
        //删除表t_organization_level
        organizationLevelMapper.deleteByOrganizationId(id);
        if (Objects.equals(organizationModel.getType(), 4)) {
            //删除的是班级，则需要删除表t_organization_line
            Long lineId = organizationLineMapper.selectLineIdByClazz(id);
            organizationLineMapper.deleteByLineId(lineId);
        }
        ContextUtil.LOG_THREADLOCAL.set("删除组织" + organizationModel.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperateLog(module = "人员管理")
    public OrganizationModel add(AddOrganizationQo addOrganizationQo) {
        OrganizationModel organizationModel = new OrganizationModel();
        //新建的为第一级，学校
        if (Objects.equals(addOrganizationQo.getType(), 0)) {
            Integer num = organizationMapper.countByTypeAndParentId(0, null);
            if (!Objects.equals(num, 0)) {
                throw new BaseException("当前已有学校，不能重复新建");
            }
        } else {
            //获取同类型组织，验证新增的名称和简称不能重名
            List<OrganizationModel> organizationModelList = null;
            if (Objects.equals(addOrganizationQo.getType(), 4)) {
                organizationModelList = organizationMapper.findByTypeAndParentIdAndGrade(addOrganizationQo.getType(), null, addOrganizationQo.getGrade());
            } else {
                organizationModelList = organizationMapper.findByTypeAndParentIdAndGrade(addOrganizationQo.getType(), null, null);
            }
            repeatNameOrSn(organizationModelList, addOrganizationQo.getName(), addOrganizationQo.getSn());
        }
        //添加数据到t_organization表中
        BeanUtils.copyProperties(addOrganizationQo, organizationModel);
        organizationModel.setValid(1);
        organizationModel.setCreateTime(new Date());
        organizationModel.setCreateUserId(ContextUtil.getId());
        organizationMapper.insertOrganization(organizationModel);
        //添加数据到t_organization_level表中
        if (!Objects.equals(addOrganizationQo.getType(), 0)) {
            OrganizationLevelModel organizationLevelModel = new OrganizationLevelModel();
            organizationLevelModel.setOrganizationId(organizationModel.getId());
            organizationLevelModel.setParentId(addOrganizationQo.getParentId());
            organizationLevelMapper.insertOrganizationLevel(organizationLevelModel);
            //如果新加的组织为班级，则需添加数据到t_organization_line表中
            if (Objects.equals(addOrganizationQo.getType(), 4)) {
                List<OrganizationLineModel> organizationLineModelList = new ArrayList<>();
                //获取当前最大的lineId
                Long maxLineId = organizationLineMapper.getMaxLineId();
                Long lineId = maxLineId + 1;
                List<Long> organizationIdList = new ArrayList<>();
                organizationIdList.add(organizationModel.getId());
                organizationIdList.add(addOrganizationQo.getParentId());
                organizationIdList.add(organizationLevelMapper.getParentIdByOrganizationId(addOrganizationQo.getParentId()));
                //获取添加学校的organizationId
                List<OrganizationModel> school = organizationMapper.findByTypeAndParentIdAndGrade(0, null, null);
                if (school != null && school.size() > 0) {
                    organizationIdList.add(school.get(0).getId());
                }
                for (Long organizationId : organizationIdList) {
                    OrganizationLineModel organizationLineModel = new OrganizationLineModel();
                    organizationLineModel.setLineId(lineId);
                    organizationLineModel.setOrganizationId(organizationId);
                    organizationLineModelList.add(organizationLineModel);
                }
                organizationLineMapper.insertOrganizationLineList(organizationLineModelList);
            }
        }
        ContextUtil.LOG_THREADLOCAL.set("新增组织" + addOrganizationQo.getName());
        return organizationModel;
    }

    @Override
    public List<OrganizationModel> getAllColleges() {
        OrganizationModel queryCollege = new OrganizationModel();
        queryCollege.setType(CategoryConstant.CATEGORY_COLLEGE);
        List<OrganizationModel> college = organizationMapper.select(queryCollege);
        return college;
    }

    @Override
    public List<OrganizationModel> getManageColleges() {
//        OrganizationModel querySchool = new OrganizationModel();
//        querySchool.setType(CategoryConstant.CATEGORY_SCHOOL);
//        List<OrganizationModel> school = organizationMapper.select(querySchool);
        RoleAndInfoDto session = SessionUtil.getSession();

        OrganizationModel queryCollege = new OrganizationModel();
        queryCollege.setType(CategoryConstant.CATEGORY_COLLEGE);
        List<OrganizationModel> college = null;
        if (Objects.equals(session.getRoleList().get(0).getName(), UserTypeConstant.SCHOOL) || Objects.equals(session.getRoleList().get(0).getName(), UserTypeConstant.ADMIN)) {
            college = organizationMapper.select(queryCollege);
        } else {
            college = organizationMapper.queryOrganizationBySn(session.getSn(), session.getRoleList().get(0).getId());
        }
//        school.addAll(college);
        return college;
    }

    @Override
    public List<OrganizationModel> getByParentAndType(Long parentId, Integer type) {
        return organizationMapper.selectByParentAndType(parentId, type, null);
    }

    @Override
    public List<String> getGradesByCollege(Long collegeId) {
        return organizationMapper.selectGradesByCollege(collegeId);
    }

    @Override
    public List<OrganizationModel> getClassesByMajorAndGrade(Long majorId, String grade) {
        return organizationMapper.selectByParentAndType(majorId, CategoryConstant.CATEGORY_CLASS, grade);
    }

    @Override
    public List<InstructorManageStuInfDto> listInstructorManageStuInfDto(String sn) {
        return organizationMapper.selectInstructorManageInf(sn);
    }

    @Override
    public List<OrganizationModel> getByParentAndTypeList(List<String> colleges, Integer type) {
        return organizationMapper.selectByParentAndTypeList(colleges, type, null);
    }

    @Override
    public List<OrganizationModel> getClassesByMajorAndGradeList(List<String> majorIdList, List<String> gradeList) {
        return organizationMapper.selectByParentAndTypeList(majorIdList, CategoryConstant.CATEGORY_CLASS, gradeList);
    }

    @Override
    public List<OrganizationModel> getByParentListAndType(List<Long> majorIdList, Integer type) {
        return organizationMapper.selectByParentListAndType(majorIdList, type, null);
    }
}