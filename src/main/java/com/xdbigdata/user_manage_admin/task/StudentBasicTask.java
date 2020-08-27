package com.xdbigdata.user_manage_admin.task;

import com.xdbigdata.framework.mybatis.datasource.DynamicDataSourceContextHolder;
import com.xdbigdata.framework.mybatis.datasource.DynamicDataSourceType;
import com.xdbigdata.user_manage_admin.constant.CategoryConstant;
import com.xdbigdata.user_manage_admin.constant.StatusConstant;
import com.xdbigdata.user_manage_admin.mapper.*;
import com.xdbigdata.user_manage_admin.mapper.oracle.StudentRemoteMapper;
import com.xdbigdata.user_manage_admin.model.*;
import com.xdbigdata.user_manage_admin.model.domain.Dictionary;
import com.xdbigdata.user_manage_admin.model.domain.StudentBasic;
import com.xdbigdata.user_manage_admin.model.domain.StudentContact;
import com.xdbigdata.user_manage_admin.model.domain.StudentEducation;
import com.xdbigdata.user_manage_admin.model.oracle.OrganizationBasic;
import com.xdbigdata.user_manage_admin.model.oracle.OrganizationIds;
import com.xdbigdata.user_manage_admin.model.oracle.StudentRemote;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * 远程数据同步定时任务
 */
@Slf4j
@Component
public class StudentBasicTask {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private StudentRemoteMapper studentRemoteMapper;

    @Autowired
    private OrganizationLevelMapper organizationLevelMapper;

    @Autowired
    private OrganizationLineMapper organizationLineMapper;

    @Autowired
    private StudentOrganizationLineMapper studentOrganizationLineMapper;

    /*******************************新加表的mapper*******************************/
    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private StudentBasicMapper studentBasicMapper;

    @Autowired
    private StudentContactMapper studentContactMapper;

    @Autowired
    private StudentEducationMapper studentEducationMapper;

    @Autowired
    private UserMapper userMapper;

    @Async
    @Scheduled(cron = "0 30 0 * * ?")
    public void sync(){
//        log.info("======================教务系统数据同步==开始======================");
//        // 远程数据库中的学生信息
//        DynamicDataSourceContextHolder.setDataSourceType(DynamicDataSourceType.SECOND);
//        List<StudentRemote> studentRemotes = studentRemoteMapper.selectAllStudent();
//        // 处理本系统数据
//        DynamicDataSourceContextHolder.setDataSourceType(DynamicDataSourceType.FIRST);
//        // 处理组织机构数据
//        handleOrganization(studentRemotes);
//
//        //TODO 处理新加表的数据
//        // t_student_basic
//        new Thread(() -> handleStudent(studentRemotes)).start();
////        handleStudent(studentRemotes);
//        // 处理学生的学籍数据
//        handleStudentOrganization(studentRemotes);
//
//        log.info("======================教务系统数据同步==完成======================");
    }

//    /**
//     * 项目启动执行一次
//     * @param args
//     * @throws Exception
//     */
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        sync();
//    }

    /**
     * 处理学生数据
     *
     * @param studentRemotes
     */
    public void handleStudent(List<StudentRemote> studentRemotes)  {
        // 学生基本信息
//        Map<String, StudentBasic> studentBasicMap = studentBasicMapper.selectAll()
//                .stream()
//                .collect(toMap(StudentBasic::getSn, Function.identity(), (k1, k2) -> k1));
        // 学生联系信息
//        Map<String, StudentContact> studentContactMap = studentContactMapper.selectAll()
//                .stream()
//                .collect(toMap(StudentContact::getSn, Function.identity(), (k1, k2) -> k1));
        // 学生学籍信息
        Map<String, StudentEducation> studentEducationMap = studentEducationMapper.selectAll()
                .stream()
                .collect(toMap(StudentEducation::getSn, Function.identity(), (k1, k2) -> k1));
        // 远程数据库中的学生信息map
        Map<String, StudentRemote> studentRemoteMap = studentRemotes
                .stream()
                .collect(toMap(StudentRemote::getSn, Function.identity(), (k1, k2) -> k1));
        // 年级map
        Map<String, Long> gradeMap = gradeMapper.selectAll()
                .stream()
                .collect(toMap(GradeModel::getName, GradeModel::getId, (k1, k2) -> k1));
        // 学生类型map
        Map<String, Long> educationMap = dictionaryMapper.selectByTypeName("学生类型")
                .stream()
                .collect(toMap(Dictionary::getName, Dictionary::getId));
        // 学籍状态map
        Map<String, Long> educationStatusMap = dictionaryMapper.selectByTypeName("学籍状态")
                .stream()
                .collect(toMap(Dictionary::getName, Dictionary::getId));

        // 循环本系统中的学生学籍信息
        studentEducationMap.forEach((sn, sb) -> {
            StudentRemote studentRemote = studentRemoteMap.get(sn);
            if (Objects.isNull(studentRemote)) {
                log.error("学号为:{}的学生在教务系统中不存在!", sn);
                return;
            }
            studentRemoteMap.remove(sn);

            StudentEducation studentEducation = studentEducationMap.get(sn);
            if (Objects.isNull(studentEducation)) {
                log.error("学号为:{}的学生学籍信息异常.", sn);
                return;
            }
            if (!StudentRemote.verify(studentRemote)) {
                log.warn("学号为:{}的学生教务系统中学籍信息不完整.", sn);
                return;
            }
            StudentEducation remote2Education = remote2Education(studentRemote, gradeMap, educationMap, educationStatusMap);

            if (!studentEducation.equals(remote2Education)) {
                log.info("学号为:{}的学生学籍有变动.", sn);
                remote2Education.setId(studentEducation.getId());
                studentEducationMapper.updateByPrimaryKeySelective(remote2Education);
            }
        });

        if (MapUtils.isEmpty(studentRemoteMap)) {
            log.info("教务系统中无新增学生信息!");
            return;
        }
    }

    /**
     * 将教务系统中的学生信息转换为本系统中的学籍信息
     *
     * @param studentRemote 教务系统中的学生信息
     * @param gradeMap 本系统中的年级map
     * @param educationMap 本系统中的学生类型map
     * @param educationStatusMap 本系统中的学籍状态map
     * @return 本系统中的学籍信息
     */
    private StudentEducation remote2Education(StudentRemote studentRemote, Map<String, Long> gradeMap, Map<String, Long> educationMap, Map<String, Long> educationStatusMap) {
        StudentEducation studentEducation = new StudentEducation();
        studentEducation.setSn(studentRemote.getSn());
        studentEducation.setCollegeCode(studentRemote.getCollegeCode());
        studentEducation.setMajorCode(studentRemote.getMajorCode());
        String classCode = studentRemote.getClassCode();
        studentEducation.setClassCode(classCode);
        if (StringUtils.isNotBlank(classCode)) {
            String grade = classCode.substring(4, 8);
            studentEducation.setGradeId(gradeMap.get(grade));
        }
        String sourceType = studentRemote.getSourceType();
        if (StringUtils.isBlank(sourceType)) {
            sourceType = "本科统招";
        }
        studentEducation.setSourceType(sourceType);
        studentEducation.setSchoolLength(studentRemote.getSchoolLength());
        studentEducation.setEducationId(educationMap.get("本科"));
        String educationStatus = studentRemote.getEducationStatus();
        if (Objects.equals("在读", educationStatus)) {
            educationStatus = "正常";
        }
        studentEducation.setEducationStatusId(educationStatusMap.get(educationStatus));
        studentEducation.setAbsentee(Objects.equals("有", studentRemote.getAbsentee()));
        studentEducation.setAtSchool(Objects.equals("是", studentRemote.getAtSchool()));
        return studentEducation;
    }

    /**
     * 处理学生的学籍信息
     *
     * @param studentRemotes 远程学生信息
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleStudentOrganization(List<StudentRemote> studentRemotes) {
        // 学号和id对应关系
        Map<String, StudentModel> studentMap = studentMapper.selectAll()
                .stream()
                .collect(toMap(StudentModel::getSn, Function.identity(), (k1, k2) -> k1));
        // 班级class sn和lineId的对应关系
        Map<String, OrganizationModel> classMap = organizationLineMapper.selectAllClassLine()
                .stream()
                .collect(toMap(OrganizationModel::getSn, Function.identity()));

        // 远程数据库中的学生信息map
        Map<String, StudentRemote> studentBasicMap = studentRemotes
                .stream()
                .collect(toMap(StudentRemote::getSn, Function.identity(), (k1, k2) -> k1));

        // 更新学生学籍信息
        List<StudentOrganizationLineModel> studentOrganizationLineModels = new ArrayList<>();
        studentMap.forEach((sn, s) -> {
            StudentRemote studentRemote = studentBasicMap.get(sn);
            if (Objects.isNull(studentRemote)) {
                log.error("The remote student not exist! -> sn = {}", sn);
                return;
            }

            String classCode = studentRemote.getClassCode();
            if (StringUtils.isBlank(classCode)) {
                log.error("The remote student's class code is blank! -> sn = {}", sn);
                return;
            }

            OrganizationModel clazz = classMap.get(classCode);
            if (clazz == null) {
                log.error("The class line id not exist! -> classSn = {}", classCode);
                return;
            }

            Long id = s.getUserId();
            StudentOrganizationLineModel studentOrganizationLineModel = new StudentOrganizationLineModel(id);
            studentOrganizationLineModel.setLineId(clazz.getId());
//             年级变化则更新年级信息
            if (!Objects.equals(clazz.getGrade(), s.getGrade())) {
                log.info("The student grade is change! sn -> {}", sn);
                studentMapper.updateStudentGrade(id, clazz.getGrade());
            }
            if (!Objects.equals(s.getStatus(), studentRemote.getStudentStatus())) {
                log.info("The student status is change! sn -> {}", sn);
                studentMapper.updateStudentStatus(id, studentRemote.getStudentStatus());
            }
            studentOrganizationLineModels.add(studentOrganizationLineModel);
        });
//        // 更新学生的学籍信息
//        if (CollectionUtils.isNotEmpty(studentOrganizationLineModels)) {
//            studentOrganizationLineMapper.deleteByStudent(studentOrganizationLineModels);
//            studentOrganizationLineMapper.insertList(studentOrganizationLineModels);
//        }

        int i = 0;
        for (String key : studentBasicMap.keySet()) {
            StudentRemote remote = studentBasicMap.get(key);
            String collegeCode = remote.getCollegeCode();
            String collegeName = remote.getCollege();
            String majorCode = remote.getMajorCode();
            String majorName = remote.getMajor();
            String classesCode = remote.getClassCode();
            String classesName = remote.getClazz();
            if(collegeCode == null || Objects.equals("",collegeCode) || collegeName == null || Objects.equals("",collegeName)){
                collegeCode = "WZXY";
            }else{
                OrganizationModel organizationModel = organizationMapper.getOrganizationData(collegeCode);
                if(organizationModel == null){
                    organizationModel = new OrganizationModel();
                    organizationModel.setType(1);
                    organizationModel.setValid(1);
                    organizationModel.setCreateTime(new Date());
                    organizationModel.setSn(collegeCode);
                    organizationModel.setName(collegeName);
                    organizationMapper.insertOrganization(organizationModel);
                }
            }
            if(majorCode == null || Objects.equals("",majorCode) || majorName == null || Objects.equals("",majorName)){
                majorCode = "WZZY";
            }else{
                OrganizationModel organizationModel = organizationMapper.getOrganizationData(majorCode);
                if(organizationModel == null){
                    organizationModel = new OrganizationModel();
                    organizationModel.setType(2);
                    organizationModel.setValid(1);
                    organizationModel.setCreateTime(new Date());
                    organizationModel.setSn(majorCode);
                    organizationModel.setName(majorName);
                    organizationMapper.insertOrganization(organizationModel);
                }
            }
            if(classesCode == null || Objects.equals("",classesCode) || classesName == null || Objects.equals("",classesName)){
                classesCode = "WZBJ";
            }else{
                OrganizationModel organizationModel = organizationMapper.getOrganizationData(classesCode);
                if(organizationModel == null){
                    organizationModel = new OrganizationModel();
                    organizationModel.setType(4);
                    organizationModel.setValid(1);
                    organizationModel.setCreateTime(new Date());
                    organizationModel.setSn(classesCode);
                    organizationModel.setName(classesName);
                    organizationModel.setGrade(remote.getGrade());
                    organizationMapper.insertOrganization(organizationModel);
                }
            }
            OrganizationModel collegeId = organizationMapper.getOrganizationData(collegeCode);
            OrganizationModel majorId = organizationMapper.getOrganizationData(majorCode);
            OrganizationModel classId = organizationMapper.getOrganizationData(classesCode);
            List<OrganizationLevelModel> organizationLevelCollege = organizationLevelMapper.queryLevel(remote.getMajorCode(),remote.getCollegeCode());
            if(organizationLevelCollege == null || organizationLevelCollege.size() == 0){
                if(remote.getMajorCode() != null && remote.getCollegeCode()!= null){
                    organizationLevelMapper.insertlevel(majorId.getId(),collegeId.getId());
                }
            }
            List<OrganizationLevelModel> organizationLevelMajor = organizationLevelMapper.queryLevel(remote.getClassCode(),remote.getMajorCode());
            if(organizationLevelMajor == null || organizationLevelMajor.size() == 0){
                if(remote.getClassCode() != null && remote.getMajorCode() != null){
                    organizationLevelMapper.insertlevel(classId.getId(),majorId.getId());
                }
            }

            Long lineId = organizationLineMapper.queryLine(collegeCode,majorCode,classesCode);

            Long maxLineId = organizationLineMapper.getMaxLineId();
            StudentOrganizationLineModel studentOrganizationLineModel = studentOrganizationLineMapper.queryLineIdByStudentSn(key);
            UserModel user = userMapper.selectUserBySn(key);
            if(lineId == null){
                organizationLineMapper.insertNewLine(collegeId.getId(),maxLineId + 1);
                organizationLineMapper.insertNewLine(majorId.getId(),maxLineId + 1);
                organizationLineMapper.insertNewLine(classId.getId(),maxLineId + 1);
                if(studentOrganizationLineModel == null || studentOrganizationLineModel.getLineId() == null){
                    if(user != null && user.getId() != null){
                        studentOrganizationLineMapper.insertStudentNewLine(user.getId(),maxLineId + 1);
                    }
                }else{
                    studentOrganizationLineModel.setLineId(maxLineId + 1);
                    studentOrganizationLineMapper.updateLineIdByStudent(maxLineId + 1,studentOrganizationLineModel.getStudentId());
                }
            }else{
                if(studentOrganizationLineModel == null || studentOrganizationLineModel.getLineId() == null){
                    if(user != null && user.getId() != null){
                        studentOrganizationLineMapper.insertStudentNewLine(user.getId(),lineId);
                    }
                }else{
                    if(lineId != studentOrganizationLineModel.getLineId()){
                        studentOrganizationLineMapper.updateLineIdByStudent(lineId,studentOrganizationLineModel.getStudentId());
                    }
                }
            }
            i ++;
            log.error("学生组织机构线同步:{},NO：{}",key,i);
        }
    }

    /**
     * 处理组织机构数据
     *
     * @param studentBasics 远程学生数据
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleOrganization(List<StudentRemote> studentBasics) {
        List<OrganizationBasic> organizationBasics = studentBasics.stream()
                .filter(StudentRemote::verify)
                .flatMap(b -> OrganizationBasic.createByStudentBasic(b).stream())
                .distinct()
                .collect(Collectors.toList());

        // 组织机构
        Map<String, OrganizationModel> organizationMap = organizationMapper.selectAll()
                .stream()
                .collect(toMap(OrganizationModel::getSn, Function.identity()));
        // 处理新增的组织机构
        List<OrganizationModel> addOrganizations = new ArrayList<>();
        List<OrganizationBasic> addOrganizationBasics = new ArrayList<>();
        organizationBasics.stream()
                .collect(toMap(OrganizationBasic::getCode, Function.identity(), (k1, k2) -> k1))
                .forEach((sn, organizationBasic) -> {
            OrganizationModel organizationModel = organizationMap.get(sn);
            if (Objects.nonNull(organizationModel)) {
                return;
            }
            log.debug("需要添加的组织机构：{}", organizationBasic);
            addOrganizations.add(createOrganization(organizationBasic));
            addOrganizationBasics.add(organizationBasic);
        });

        log.info("需要添加的组织机构有[{}]条", addOrganizations.size());
        if (CollectionUtils.isEmpty(addOrganizations)) {
            return;
        }
        organizationMapper.insertList(addOrganizations);
        // 添加组织机构关系
        organizationLevelMapper.insertListByBasic(addOrganizationBasics);

        // 处理组织机构line
        handleLine(addOrganizations);
    }

    /**
     * 处理组织机构line
     *
     * @param addOrganizations 需要添加的组织机构集合
     */
    private void handleLine(List<OrganizationModel> addOrganizations) {
        // 处理line
        List<String> classSns = addOrganizations.stream()
                .filter(o -> CategoryConstant.CATEGORY_CLASS.equals(o.getType()))
                .map(OrganizationModel::getSn)
                .collect(Collectors.toList());
        // 班级对应的clazz、major、college、school的id对象
        List<OrganizationIds> organizationIds = organizationLevelMapper.selectOrganizationIdsByClass(classSns);
        // 获取最大的line id
        Long maxLineId = organizationLineMapper.getMaxLineId();
        maxLineId = maxLineId == null ? 0 : maxLineId;
        AtomicLong atomicLong = new AtomicLong(maxLineId);
        // 生成line mode
        List<OrganizationLineModel> organizationLineModels = organizationIds.stream()
                .flatMap(oi -> createLineByOrganization(oi, atomicLong.incrementAndGet()).stream())
                .collect(Collectors.toList());
        organizationLineMapper.insertOrganizationLineList(organizationLineModels);
    }

    /**
     * 根据组织机构ids创建line model集合
     *
     * @param organizationIds 组织机构ids
     * @param lineId line id
     * @return
     */
    private List<OrganizationLineModel> createLineByOrganization(OrganizationIds organizationIds, Long lineId) {
        List<OrganizationLineModel> results = new ArrayList<>();
        results.add(new OrganizationLineModel(organizationIds.getClazzId(), lineId));
        results.add(new OrganizationLineModel(organizationIds.getMajorId(), lineId));
        results.add(new OrganizationLineModel(organizationIds.getCollegeId(), lineId));
        results.add(new OrganizationLineModel(organizationIds.getSchoolId(), lineId));
        return results;
    }

    /**
     * 创建组织机构
     *
     * @param organizationBasic 组织机构信息
     * @return 组织机构
     */
    private OrganizationModel createOrganization(OrganizationBasic organizationBasic) {
        OrganizationModel organizationModel = new OrganizationModel();
        String code = organizationBasic.getCode();
        organizationModel.setSn(code);
        organizationModel.setName(organizationBasic.getName());
        organizationModel.setType(organizationBasic.getType());
        organizationModel.setValid(StatusConstant.STATUS_VALID);
        if (CategoryConstant.CATEGORY_CLASS.equals(organizationBasic.getType())) {
            organizationModel.setGrade(code.substring(4, 8));
        }
        return organizationModel;
    }
}
