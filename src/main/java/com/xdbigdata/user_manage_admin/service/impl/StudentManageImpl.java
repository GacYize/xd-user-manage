package com.xdbigdata.user_manage_admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.xdbigdata.framework.common.exception.BaseException;
import com.xdbigdata.framework.fastdfs.FastDFSClient;
import com.xdbigdata.framework.web.model.PageQuery;
import com.xdbigdata.user_manage_admin.constant.CommonConstant;
import com.xdbigdata.user_manage_admin.enums.AuditStatus;
import com.xdbigdata.user_manage_admin.enums.UserType;
import com.xdbigdata.user_manage_admin.feign.*;
import com.xdbigdata.user_manage_admin.mapper.*;
import com.xdbigdata.user_manage_admin.mapper.oracle.StudentRemoteMapper;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.domain.*;
import com.xdbigdata.user_manage_admin.model.dto.student.StudentModifyDto;
import com.xdbigdata.user_manage_admin.model.vo.StudentInfoVo;
import com.xdbigdata.user_manage_admin.model.vo.SubmitModifyDetailVo;
import com.xdbigdata.user_manage_admin.model.vo.SubmitModifyVo;
import com.xdbigdata.user_manage_admin.model.vo.teacher.InstructorNameVO;
import com.xdbigdata.user_manage_admin.service.StudentManageService;
import com.xdbigdata.user_manage_admin.util.ContextUtil;
import com.xdbigdata.user_manage_admin.util.RemoteFeignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.Strings;
import org.csource.fastdfs.StorageClient1;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ghj
 * @date 2019/2/18
 */
@Slf4j
@Service
public class StudentManageImpl implements StudentManageService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private OrganizationLineMapper organizationLineMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    /******************************第二次修改注入的信息******************************/
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
    public StudentInfoVo getStudentInfo(String sn) {
        StudentInfoVo vo = new StudentInfoVo();
        // 设置入学照片
        UserModel user = userMapper.selectUserBySn(sn);
        if (Objects.isNull(user)) {
            throw new BaseException("用户数据错误");
        }
        vo.setPicUrl(user.getPicUrl());

        StudentModifyAudit studentModifyAudit = null;
        // 如果是学生登录
        if (UserType.STUDENT.equals(ContextUtil.getUserType())) {
            // 查询有无待审核的数据
            StudentModifyAudit auditCondition = new StudentModifyAudit();
            auditCondition.setSn(sn);
            auditCondition.setAuditStatus(AuditStatus.WAIT.getType());
            studentModifyAudit = studentModifyAuditMapper.selectOne(auditCondition);
        }


        // 有待审核的数据
        if (Objects.nonNull(studentModifyAudit)) {
            // 修改状态: 0:可以修改, 1:不能修改
            vo.setModifyStatus(CommonConstant.ONE);
            vo.setStudentBasic(studentModifyAudit.getStudentBasicModify());
            vo.setStudentContact(studentModifyAudit.getStudentContactModify());
            vo.setStudentEducation(studentModifyAudit.getStudentEducationModify());
            vo.setStudentFamilies(studentModifyAudit.getStudentFamilies());
        } else {
            // 修改状态: 0:可以修改, 1:不能修改
            vo.setModifyStatus(CommonConstant.ZERO);

            StudentBasic studentBasic = studentBasicMapper.selectBySn(sn);
            vo.setStudentBasic(studentBasic);

            StudentContact studentContact = studentContactMapper.selectBySn(sn);
            vo.setStudentContact(studentContact);
            StudentEducation studentEducation = studentEducationMapper.selectBySn(sn);
            vo.setStudentEducation(studentEducation);
            List<StudentFamily> studentFamilies = studentFamilyMapper.selectBySn(sn);
            vo.setStudentFamilies(studentFamilies);
        }

        // 设置其它系统的信息
        vo.setDorm(RemoteFeignUtils.getStudentDorm(sn));
        vo.setClassTeacher(userMapper.selectClassTeacherBySn(sn));
        vo.setDormitoryArea(RemoteFeignUtils.getDormitoryArea(sn));
        vo.setPunishStudentDetailVo(RemoteFeignUtils.getPunishInf(sn));
        vo.setStudentScholarships(RemoteFeignUtils.getStudentScholarship(sn));
        // 设置辅导员姓名
//        UserModel instructor = roleMapper.selectInstructorByStudentId(user.getId());
//        String instructor = studentMapper.selectnstructorBySn(user.getId());
//        if (Objects.nonNull(instructor)) {
//            vo.setInstructorName(instructor);
//        }
        // 设置辅导员姓名
        vo.setInstructorName(userMapper.selectInstructorBySn(sn));
//        List<InstructorNameVO> instructorNameVOS = userMapper.queryInstructorByCode(vo.getStudentEducation().getClassCode());
//        if (instructorNameVOS != null && instructorNameVOS.size() > 0) {
//            for (int i = 0; i < instructorNameVOS.size(); i++) {
//                if (i == 0) {
//                    vo.setInstructorName(instructorNameVOS.get(i).getInstructorName());
//                } else {
//                    vo.setInstructorName(vo.getInstructorName() + "," + instructorNameVOS.get(i).getInstructorName());
//                }
//            }
//        }
        return vo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map getStudentAllInfo(String sn) {
        Map map = new HashMap();
        map.put("scores", null);
        UserModel user = userMapper.selectUserBySn(sn);
        StudentBasic studentBasic = studentBasicMapper.selectBySn(sn); // 基本信息
        map.put("basic", studentBasic);
        StudentContact studentContact = studentContactMapper.selectBySn(sn); // 通讯信息
        map.put("contact", studentContact);
        StudentEducation studentEducation = studentEducationMapper.selectBySn(sn); // 学籍和入学信息
        map.put("education", studentEducation);

        List<StudentFamily> studentFamilies = studentFamilyMapper.selectBySn(sn); // 家庭信息
        List<StudentFamily> parents = new ArrayList<>();
        List<StudentFamily> consanguinitys = new ArrayList<>();
        List<StudentFamily> linkmans = new ArrayList<>();

        for (StudentFamily studentFamily : studentFamilies) {
            if (studentFamily.getType() == 0) {
                parents.add(studentFamily);
            } else if (studentFamily.getType() == 1) {
                consanguinitys.add(studentFamily);
            } else if (studentFamily.getType() == 2) {
                linkmans.add(studentFamily);
            }
        }
        map.put("parents", parents);
        map.put("consanguinitys", consanguinitys);
        map.put("linkmans", linkmans);
        // 设置其它系统的信息
        map.put("studentDorm", RemoteFeignUtils.getStudentDormInf(sn));

        map.put("classTeacher", userMapper.selectClassTeacherBySn(sn));//班主任
        map.put("instructor", userMapper.selectInstructorBySn(sn));//辅导员

        map.put("dormitoryArea", RemoteFeignUtils.getDormitoryArea(sn));
        map.put("scholarships", RemoteFeignUtils.getScRe(sn)); //奖学金
        map.put("violations", RemoteFeignUtils.getPunishList(sn));

        map.put("grants", null); // 助学金
        map.put("scholarshipgs", null);
        map.put("identifys", null); // 困难认定
        map.put("subsidys", null); // 困难补助
        map.put("borrows", null); // 无息借款
        map.put("studyings", null); // 勤工助学
        map.put("tuitions", null); // 学费减免
        map.put("gssls", null); // 国家助学贷款
        map.put("compensations", null); // 义务兵代偿
        map.put("landloans", null); // 生源地助学贷款
        if (Objects.isNull(user)) {
            throw new BaseException("用户数据错误");
        }
        if (user.getPicUrl() == null || "".equals(user.getPicUrl())) {
            map.put("picUrl", null);
        } else {
            try {
                map.put("picUrl", getImageStr(new StorageClient1().download_file1(user.getPicUrl())));
            } catch (Exception e) {
                map.put("picUrl", null);
                log.error("获取照片信息错误" + e);
            }
        }
        return map;
    }

    @Override
    @Transactional
    public void commitUpdateInfo(StudentModifyDto studentModifyDto) {
        String sn = ContextUtil.getSn();
        // 查询有无待审核的数据
        StudentModifyAudit auditConditioin = new StudentModifyAudit();
        auditConditioin.setSn(sn);
        auditConditioin.setAuditStatus(AuditStatus.WAIT.getType());
        int waitCount = studentModifyAuditMapper.selectCount(auditConditioin);
        // 存在待审核的修改
        if (waitCount > 0) {
            log.error("{}:{} 已提交有信息修改", sn, ContextUtil.getName());
            throw new BaseException("已存在待审核信息，不可再次提交");
        }

        StudentModifyAudit studentModifyAudit = new StudentModifyAudit();
        // 设置提交信息
        studentModifyAudit.setAuditStatus(AuditStatus.WAIT.getType());
        studentModifyAudit.setSubmitTime(new Date());
        studentModifyAudit.setSn(sn);
        // 设置历史信息
        StudentBasic studentBasic = studentBasicMapper.selectBySn(sn);
        if (Objects.nonNull(studentBasic)) {
            studentModifyAudit.setBasicHistory(JSON.toJSONString(studentBasic));
        }
        StudentContact studentContact = studentContactMapper.selectBySn(sn);
        if (Objects.nonNull(studentContact)) {
            studentModifyAudit.setContactHistory(JSON.toJSONString(studentContact));
        }
        StudentEducation studentEducation = studentEducationMapper.selectBySn(sn);
        if (Objects.nonNull(studentEducation)) {
            studentModifyAudit.setEducationHistory(JSON.toJSONString(studentEducation));
        }
        // 设置修改信息
        StudentBasic studentBasicModify = studentModifyDto.getStudentBasic();
        if (Objects.nonNull(studentBasicModify.getCensusRegisterType())) {
            studentBasicModify.setCensusRegisterTypeStr(dictionaryMapper.selectByPrimaryKey(studentBasicModify.getCensusRegisterType()).getName());
        }
        if (Objects.nonNull(studentBasicModify.getPersonnelType())) {
            studentBasicModify.setPersonnelTypeStr(dictionaryMapper.selectByPrimaryKey(studentBasicModify.getPersonnelType()).getName());
        }
        if (Objects.nonNull(studentBasicModify.getPersonnelStatus())) {
            studentBasicModify.setPersonnelStatusStr(dictionaryMapper.selectByPrimaryKey(studentBasicModify.getPersonnelStatus()).getName());
        }
        studentBasicModify.setSn(sn);
        studentModifyAudit.setBasicModify(JSON.toJSONString(studentBasicModify));

        StudentContact studentContactModify = studentModifyDto.getStudentContact();
        studentContactModify.setSn(sn);
        studentModifyAudit.setContactModify(JSON.toJSONString(studentContactModify));

        StudentEducation studentEducationModify = studentModifyDto.getStudentEducation();
        setStudentEducationModify(studentEducation, studentEducationModify);
        studentModifyAudit.setEducationModify(JSON.toJSONString(studentEducationModify));

        List<StudentFamily> studentFamilies = studentModifyDto.getStudentFamilies()
                .stream()
                .map(f -> f.setSn(sn))
                .collect(Collectors.toList());
        studentModifyAudit.setFamiliesModify(JSON.toJSONString(studentFamilies));

        studentModifyAuditMapper.insertSelective(studentModifyAudit);
    }

    /**
     * 设置学生学籍信息
     *
     * @param studentEducation       修改前的学籍信息
     * @param studentEducationModify 修改的学籍信息
     */
    private void setStudentEducationModify(StudentEducation studentEducation, StudentEducation studentEducationModify) {
        String[] ignoreProperties = {
                "id", "graduateDate", "educationId", "enterDate",
                "domicilePlaceCode", "sourcePlaceCode", "highSchool"
        };
        BeanUtils.copyProperties(studentEducation, studentEducationModify, ignoreProperties);
    }

    @Override
    public List<SubmitModifyVo> getCommitHistoryList(PageQuery query) {
//        PageHelper.startPage(query.getPgCt(), query.getPgSz());
        List<SubmitModifyVo> page = studentModifyAuditMapper.selectBySn(ContextUtil.getSn());
        return page;
    }

    @Override
    public SubmitModifyDetailVo getSubmitModifyDetail(Long id) {
        SubmitModifyDetailVo vo = new SubmitModifyDetailVo();

        // 查询学生提交的修改记录
        StudentModifyAudit studentModifyAudit = studentModifyAuditMapper.selectByPrimaryKey(id);
        if (Objects.isNull(studentModifyAudit)) {
            throw new BaseException("学生提交的修改记录不存在");
        }

        String sn = studentModifyAudit.getSn();
        // 设置入学照片
        UserModel user = userMapper.selectUserBySn(sn);
        if (Objects.isNull(user)) {
            throw new BaseException("用户数据错误");
        }
        vo.setPicUrl(user.getPicUrl());

        vo.setStudentModifyAudit(studentModifyAudit);

        // 设置其它系统的信息
        vo.setDorm(RemoteFeignUtils.getStudentDorm(sn));
        vo.setClassTeacher(userMapper.selectClassTeacherBySn(sn));
        vo.setDormitoryArea(RemoteFeignUtils.getDormitoryArea(sn));

        // 设置辅导员姓名
        UserModel instructor = roleMapper.selectInstructorByStudentId(user.getId());
        if (Objects.nonNull(instructor)) {
            vo.setInstructorName(userMapper.selectInstructorBySn(sn));
        }
        return vo;
    }

    @Override
    public String updateLifeHead(MultipartFile file, String sn) {
        try {
            String url = FastDFSClient.uploadWithMultipart(file);
            UserModel userModel = userMapper.selectUserBySn(sn);
            if (Objects.isNull(userModel)) {
                throw new BaseException("学生基本信息不存在");
            }
            log.error("上传到FastDFS的文件: {}", url);
            userModel.setPicUrl(url);
            userMapper.updateByPrimaryKeySelective(userModel);
            return url;
        } catch (Exception e) {
            throw new BaseException("上传图片失败", e);
        }
    }


    public String getImageStr(byte[] data) {
        if (data != null && data.length > 0) {
            BASE64Encoder encoder = new BASE64Encoder();

            return encoder.encode(data);
        } else {
            return null;
        }
    }


//    public static String getBase64ByImgUrl(String url){
//        String suffix = url.substring(url.lastIndexOf(".") + 1);
//        try {
//            URL urls = new URL(url);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            Image image = Toolkit.getDefaultToolkit().getImage(urls);
//            BufferedImage biOut = toBufferedImage(image);
//            ImageIO.write(biOut, suffix, baos);
//            String base64Str = Base64Utils.encode(baos.toByteArray()).toString();
//            return base64Str;
//        } catch (Exception e) {
//            return "";
//        }
//
//    }
//
//    public static BufferedImage toBufferedImage(Image image) {
//        if (image instanceof BufferedImage) {
//            return (BufferedImage) image;
//        }
//        // This code ensures that all the pixels in the image are loaded
//        image = new ImageIcon(image).getImage();
//        BufferedImage bimage = null;
//        GraphicsEnvironment ge = GraphicsEnvironment
//                .getLocalGraphicsEnvironment();
//        try {
//            int transparency = Transparency.OPAQUE;
//            GraphicsDevice gs = ge.getDefaultScreenDevice();
//            GraphicsConfiguration gc = gs.getDefaultConfiguration();
//            bimage = gc.createCompatibleImage(image.getWidth(null),
//                    image.getHeight(null), transparency);
//        } catch (HeadlessException e) {
//            // The system does not have a screen
//        }
//        if (bimage == null) {
//            // Create a buffered image using the default color model
//            int type = BufferedImage.TYPE_INT_RGB;
//            bimage = new BufferedImage(image.getWidth(null),
//                    image.getHeight(null), type);
//        }
//        // Copy image to buffered image
//        Graphics g = bimage.createGraphics();
//        // Paint the image onto the buffered image
//        g.drawImage(image, 0, 0, null);
//        g.dispose();
//        return bimage;
//    }
}
