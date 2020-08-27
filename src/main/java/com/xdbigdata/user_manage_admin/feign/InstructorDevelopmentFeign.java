package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "instructordevelopment")
public interface InstructorDevelopmentFeign {

    /**
     * 根据班级id查询班主任姓名
     *
     * @param classId 班级id
     * @return 班级对应的班主任姓名
     */
    @RequestMapping(value = "/open/getTeacherByClassId/{classId}", method = RequestMethod.GET)
    JsonResponse<String> getClassTeacher(@PathVariable("classId") Long classId);
}
