package com.xdbigdata.user_manage_admin.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.framework.common.model.PageResult;
import com.xdbigdata.framework.mybatis.model.MybatisPageResult;
import com.xdbigdata.user_manage_admin.enums.AuditStatus;
import com.xdbigdata.user_manage_admin.mapper.*;
import com.xdbigdata.user_manage_admin.model.StudentModel;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.domain.*;
import com.xdbigdata.user_manage_admin.model.qo.updatecityQO;
import com.xdbigdata.user_manage_admin.model.vo.ManageScope;
import com.xdbigdata.user_manage_admin.model.qo.student.StudentApplyListQo;
import com.xdbigdata.user_manage_admin.service.StudentModifyAuditService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentApplyListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * StudentModifyAudit service implement
 *
 * @author lshaci
 */
@Service
@Slf4j
public class StudentModifyAuditServiceImpl implements StudentModifyAuditService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private NationMapper nationMapper;

    @Autowired
    private PoliticStatusMapper politicStatusMapper;

    @Autowired
    private UserManagementMapper userManagementMapper;

    @Autowired
    private StudentBasicMapper studentBasicMapper;

    @Autowired
    private StudentContactMapper studentContactMapper;

    @Autowired
    private StudentEducationMapper studentEducationMapper;

    @Autowired
    private StudentModifyAuditMapper studentModifyAuditMapper;

    @Autowired
    private StudentFamilyMapper studentFamilyMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;


    @Override
    public PageResult<StudentApplyListVo> getAuditList(StudentApplyListQo query) {
        setManageScope(query);
        PageHelper.startPage(query.getPgCt(), query.getPgSz());
        List<StudentApplyListVo> studentApplyListVos = studentModifyAuditMapper.selectList(query);
        Page<StudentApplyListVo> vos = (Page<StudentApplyListVo>) studentApplyListVos;
        return new MybatisPageResult<>(vos);
    }

    /**
     * 设置学生列表查询信息中的管辖范围
     *
     * @param studentApplyListQo 学生信息列表查询对象
     */
    private void setManageScope(StudentApplyListQo studentApplyListQo) {
        List<ManageScope> manageScopes = userManagementMapper.selectManageScope(ContextUtil.getId(), ContextUtil.getRoleId());
        if (CollectionUtils.isEmpty(manageScopes)) {
            throw new BaseException("当前登录用户角色未配置管辖范围");
        }
        studentApplyListQo.setManageScopes(manageScopes);
    }

    @Override
    @Transactional
    public void audit(Long id, AuditStatus auditStatus, String reason) {
        StudentModifyAudit studentModifyAudit = studentModifyAuditMapper.selectByPrimaryKey(id);
        if (Objects.isNull(studentModifyAudit)) {
            throw new BaseException("学生提交的修改信息不存在");
        }

        if (!Objects.equals(AuditStatus.WAIT.getType(), studentModifyAudit.getAuditStatus())) {
            throw new BaseException("该条申请已审核，请勿重复操作");
        }
        completeAudit(id, auditStatus, reason);
        // 如果是审核通过，则写数据到正式表
        if (Objects.equals(AuditStatus.PASS, auditStatus)) {
            updateStudent(studentModifyAudit);
        }
    }

    /**
     * 更新学生信息
     *
     * @param studentModifyAudit 学生提交的修改信息
     */
    private void updateStudent(StudentModifyAudit studentModifyAudit) {
        StudentBasic studentBasicModify = studentModifyAudit.getStudentBasicModify();
        StudentContact studentContactModify = studentModifyAudit.getStudentContactModify();
        StudentEducation studentEducationModify = studentModifyAudit.getStudentEducationModify();
        List<StudentFamily> studentFamilies = studentModifyAudit.getStudentFamilies();

        if (Objects.isNull(studentBasicModify) || Objects.isNull(studentContactModify) ||
            Objects.isNull(studentEducationModify) || CollectionUtils.isEmpty(studentFamilies)) {
            throw new BaseException("学生提交的修改信息不完整");
        }
        // 更新学生对应的user表信息
        updateUserInfo(studentModifyAudit);
        // 更新学生对应的student表信息
        updateStudentInfo(studentModifyAudit);

        String sn = studentModifyAudit.getSn();
        // t_student_basic
        StudentBasic dbStudentBasic = studentBasicMapper.selectBySn(sn);
        if (Objects.isNull(dbStudentBasic)) {
            log.warn("学号为[{}]的学生基本信息不存在.", sn);
            studentBasicMapper.insertSelective(studentBasicModify);
        } else {
            studentBasicModify.setId(dbStudentBasic.getId());
            studentBasicMapper.updateByPrimaryKeySelective(studentBasicModify);
        }
        // t_student_contact
        StudentContact dbStudentContact = studentContactMapper.selectBySn(sn);
        if (Objects.isNull(dbStudentContact)) {
            log.warn("学号为[{}]的学生联系信息不存在.", sn);
            studentContactMapper.insertSelective(studentContactModify);
        } else {
            studentContactModify.setId(dbStudentContact.getId());
            studentContactMapper.updateByPrimaryKeySelective(studentContactModify);
        }
        // t_student_education
        StudentEducation dbStudentEducation = studentEducationMapper.selectBySn(sn);
        if (Objects.isNull(dbStudentEducation)) {
            log.warn("学号为[{}]的学生学籍信息不存在.", sn);
            studentEducationMapper.insertSelective(studentEducationModify);
        } else {
            studentEducationModify.setId(dbStudentEducation.getId());
            studentEducationMapper.updateByPrimaryKeySelective(studentEducationModify);
        }
        // t_student_family
        studentFamilyMapper.deleteBySn(sn);
        studentFamilyMapper.insertList(studentFamilies);
    }

    /**
     * 更新学生对应的student表信息
     *
     * @param studentModifyAudit
     */
    private void updateStudentInfo(StudentModifyAudit studentModifyAudit) {
        String sn = studentModifyAudit.getSn();
        StudentBasic studentBasicModify = studentModifyAudit.getStudentBasicModify();
        StudentContact studentContactModify = studentModifyAudit.getStudentContactModify();
        StudentEducation studentEducationModify = studentModifyAudit.getStudentEducationModify();

        StudentModel dbStudentModel = studentMapper.selectBySn(sn);

        StudentModel studentModel = new StudentModel();
        String homeAddress = studentContactModify.getFamilyPlace() + " " + studentContactModify.getFamilyDetailAddress();
        studentModel.setSn(sn);
        studentModel.setHomeAddress(homeAddress);
        studentModel.setHomePhone(studentContactModify.getFamilyPhone());
        studentModel.setEnterDate(studentEducationModify.getEnterDate());
        studentModel.setGraduateDate(studentEducationModify.getGraduateDate());
        studentModel.setHobby(studentBasicModify.getHobby());
        studentModel.setReligion(studentBasicModify.getReligion());
        studentModel.setGrade(studentBasicModify.getGrade());
        studentModel.setSourcePlace(studentEducationModify.getSourcePlace());
        studentModel.setEducationLevel(studentEducationModify.getEducation());

        updatecityQO updatecityQO = new updatecityQO();
        updatecityQO.setSn(sn);
        updatecityQO.setDeparturePlace(studentBasicModify.getDeparturePlace());
        updatecityQO.setDestinationPlace(studentBasicModify.getDestinationPlace());
        studentMapper.updateDrivingRange(updatecityQO);
        if (Objects.isNull(dbStudentModel)) {
            studentMapper.insertSelective(studentModel);
        } else {
            studentModel.setId(dbStudentModel.getId());
            studentMapper.updateByPrimaryKeySelective(studentModel);
        }
    }

    /**
     * 更新学生对应的user表信息
     *
     * @param studentModifyAudit
     */
    private void updateUserInfo(StudentModifyAudit studentModifyAudit) {
        String sn = studentModifyAudit.getSn();
        StudentBasic studentBasicModify = studentModifyAudit.getStudentBasicModify();
        StudentContact studentContactModify = studentModifyAudit.getStudentContactModify();
        StudentEducation studentEducationModify = studentModifyAudit.getStudentEducationModify();

        UserModel dbUserModel = userMapper.selectUserBySn(sn);
        if (Objects.isNull(dbUserModel)) {
            throw new BaseException("学生用户信息出错");
        }
        UserModel userModel = new UserModel();

        BeanUtils.copyProperties(studentBasicModify, userModel);
        BeanUtils.copyProperties(studentContactModify, userModel);
        BeanUtils.copyProperties(studentEducationModify, userModel);

        String gender = studentBasicModify.getGender();
        if (StringUtils.isBlank(gender)) {
            userModel.setGender(-1);
        } else {
            switch (gender) {
                case "男性": userModel.setGender(1);
                case "女性": userModel.setGender(2);
                default:  userModel.setGender(-1);
            }
        }

        String nation = studentBasicModify.getNation();
        if (StringUtils.isNotBlank(nation)) {
            Long nationId = nationMapper.selectIdByName(nation);
            userModel.setNationId(nationId);
        }

        Long politicsStatusId = studentBasicModify.getPoliticsStatusId();
        if (Objects.nonNull(politicsStatusId)) {
            Integer politicsStatus = dictionaryMapper.findById(politicsStatusId);
            userModel.setPoliticalStatus(politicsStatus);
        }

        userModel.setOriginCode(studentBasicModify.getNativePlaceCode());
        userModel.setOpenBank(studentBasicModify.getOpeningBank());
        userModel.setBankNumber(studentBasicModify.getBankCard());
        userModel.setId(dbUserModel.getId());

        userMapper.updateByPrimaryKeySelective(userModel);
    }

    /**
     * 完成审核
     *
     * @param id 修改数据id
     * @param auditStatus 审核结果
     * @param reason 拒绝理由
     */
    private void completeAudit(Long id, AuditStatus auditStatus, String reason) {
        StudentModifyAudit auditResult = new StudentModifyAudit();
        auditResult.setId(id);
        auditResult.setAuditStatus(auditStatus.getType());
        auditResult.setAuditSn(ContextUtil.getSn());
        auditResult.setAuditTime(new Date());
        auditResult.setAuditComment(reason);
        studentModifyAuditMapper.updateByPrimaryKeySelective(auditResult);
    }

}
