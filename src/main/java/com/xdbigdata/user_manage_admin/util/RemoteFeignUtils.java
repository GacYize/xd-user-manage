package com.xdbigdata.user_manage_admin.util;

import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.feign.AccommodationFeign;
import com.xdbigdata.user_manage_admin.feign.InstructorDevelopmentFeign;
import com.xdbigdata.user_manage_admin.feign.PunishFeign;
import com.xdbigdata.user_manage_admin.feign.ScholarshipFeign;
import com.xdbigdata.user_manage_admin.mapper.StudentMapper;
import com.xdbigdata.user_manage_admin.model.AdjustmentRecordDto;
import com.xdbigdata.user_manage_admin.model.dto.api.PunishStudentQo;
import com.xdbigdata.user_manage_admin.model.dto.api.RecordQuery;
import com.xdbigdata.user_manage_admin.model.dto.student.PunishStudentDetailVo;
import com.xdbigdata.user_manage_admin.model.dto.student.StudentScholarship;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 宿舍管理远程接口工具类
 */
@Slf4j
@Component
public class RemoteFeignUtils {

    private static StudentMapper studentMapper;

    private static AccommodationFeign accommodationFeign;

    private static InstructorDevelopmentFeign instructorDevelopmentFeign;

    private static PunishFeign punishFeign;

    private static ScholarshipFeign scholarshipFeign;

    @Autowired
    public void setScholarshipFeign(ScholarshipFeign scholarshipFeign) {
        RemoteFeignUtils.scholarshipFeign = scholarshipFeign;
    }

    @Autowired
    public void setPunishFeign(PunishFeign punishFeign) {
        RemoteFeignUtils.punishFeign = punishFeign;
    }

    @Autowired
    public void setStudentMapper(StudentMapper studentMapper) {
        RemoteFeignUtils.studentMapper = studentMapper;
    }

    @Autowired
    public void setAccommodationFeign(AccommodationFeign accommodationFeign) {
        RemoteFeignUtils.accommodationFeign = accommodationFeign;
    }

    @Autowired
    public void setInstructorDevelopmentFeign(InstructorDevelopmentFeign instructorDevelopmentFeign) {
        RemoteFeignUtils.instructorDevelopmentFeign = instructorDevelopmentFeign;
    }


    public static List<StudentScholarship> getStudentScholarship(String sn) {
        sn = sn.trim();
        JsonResponse<List<StudentScholarship>> response;
        try {
            response = scholarshipFeign.getScholarship(sn);
        } catch (Exception e) {
            log.error("从奖学金管理获取学生[{}]处分失败", sn);
            return null;
        }
        if (response == null || !response.isStatus()) {
            log.error("从奖学金管理获取学生[{}]处分错误", sn);
            return null;
        }
        return response.getData();
    }

    public static List<PunishStudentDetailVo> getPunishInf(String sn) {
        sn = sn.trim();
        JsonResponse<List<PunishStudentDetailVo>> response;
        try {
            response = punishFeign.getPunishInfBySn(sn);
        } catch (Exception e) {
            log.error("从处分管理获取学生[{}]处分失败", sn);
            return null;
        }
        if (response == null || !response.isStatus()) {
            log.error("从处分管理获取学生[{}]处分错误", sn);
            return null;
        }
        return response.getData();
    }

    public static Object getPunishList(String sn) {
        sn = sn.trim();
        JsonResponse response;
        try {
            PunishStudentQo punishStudentQo = new PunishStudentQo();
            punishStudentQo.setSn(sn);
            response = punishFeign.getPunishInfBySn(punishStudentQo);
        } catch (Exception e) {
            log.error("从处分管理获取失败");
            return null;
        }
        if (response == null || !response.isStatus()) {
            log.error("从处分管理获取错误");
            return null;
        }
        return response.getData();
    }

    public static Object getScRe(String sn) {
        sn = sn.trim();
        JsonResponse response;
        try {
            RecordQuery recordQuery = new RecordQuery();
            recordQuery.setSn(sn);
            response = scholarshipFeign.getAllLoanList(recordQuery);
        } catch (Exception e) {
            log.error("从获奖记录失败");
            return null;
        }
        if (response == null || !response.isStatus()) {
            log.error("从获奖记录错误");
            return null;
        }
        return response.getData();
    }

    /**
     * 根据学生sn查询宿舍号
     *
     * @param sn 学生sn
     * @return 宿舍号
     */
    public static String getStudentDorm(String sn) {
        if (StringUtils.isBlank(sn)) {
            return null;
        }
        sn = sn.trim();
        JsonResponse<String> response;
        try {
            response = accommodationFeign.getStudentDorm(sn);
        } catch (Exception e) {
            log.error("从宿舍管理获取学生[{}]宿舍号失败", sn);
            return null;
        }
        if (response == null || !response.isStatus()) {
            log.error("宿舍管理学生[{}]宿舍号信息错误", sn);
            return null;
        }
        return response.getData();
    }

    public static Object getStudentDormInf(String sn) {
        if (StringUtils.isBlank(sn)) {
            return null;
        }
        sn = sn.trim();
        JsonResponse response;
        try {
            response = accommodationFeign.getBedDetailBySn(sn);
        } catch (Exception e) {
            log.error("从宿舍管理获取学生[{}]宿舍号失败", sn);
            return null;
        }
        if (response == null || !response.isStatus()) {
            log.error("宿舍管理学生[{}]宿舍号信息错误", sn);
            return null;
        }

        return response.getData();
    }

    /**
     * 根据学号获取宿舍分布区
     *
     * @param sn 学号
     * @return
     */
    public static String getDormitoryArea(String sn) {
        if (StringUtils.isBlank(sn)) {
            return null;
        }
        sn = sn.trim();
        JsonResponse<String> response = null;
        try {
            response = accommodationFeign.getStudentDormitoryArea(sn);
        } catch (Exception e) {
            log.error("从宿舍管理获取学生[{}]宿舍分布区失败", sn);
            return null;
        }
        if (response == null || !response.isStatus()) {
            log.error("宿舍管理学生[{}]宿舍分布区错误", sn);
            return null;
        }
        return response.getData();
    }


    /**
     * 查询学生所属校区，宿舍
     */
    public static List<AdjustmentRecordDto> getDormitoryAreaAndAdjust() {
        JsonResponse<List<AdjustmentRecordDto>> response = null;
        try {
            response = accommodationFeign.getLastAdjust();
        } catch (Exception e) {
            log.error("从宿舍管理获取学生[{}]宿舍分布区失败");
            return null;
        }
        if (response == null || !response.isStatus()) {
            log.error("宿舍管理学生[{}]宿舍分布区错误");
            return null;
        }
        return response.getData();
    }

    /**
     * 根据班级id查询班主任姓名
     *
     * @param classId 班级id
     * @return 班主任姓名
     */
    public static String getClassTeacher(Long classId) {
        if (Objects.isNull(classId)) {
            return null;
        }
        JsonResponse<String> response;
        try {
            response = instructorDevelopmentFeign.getClassTeacher(classId);
        } catch (Exception e) {
            log.error("从辅导员发展获取班主任[{}]失败", classId);
            return null;
        }
        if (response == null || !response.isStatus()) {
            log.error("辅导员发展中无[{}]班主任信息", classId);
            return null;
        }
        return response.getData();
    }

    /**
     * 根据学生学号获取班主任姓名
     *
     * @param sn 学号
     * @return 班主任姓名
     */
    public static String getClassTeacher(String sn) {
        if (StringUtils.isBlank(sn)) {
            return null;
        }
        Long classId = studentMapper.selectClassId(sn);
        return getClassTeacher(classId);
    }
}

