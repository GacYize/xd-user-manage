package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 勤工助学
 *
 * @author shuhan
 * @create 2019-02-21 10:27
 */
@FeignClient(value = "abtc-diligent-study")
public interface IDiligentStudytsService {


    /**
     * 获取学生勤工助学列表
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/common/studentSalaryList/{sn}", method = RequestMethod.GET)
    JsonResponse getStudentSalaryList(@PathVariable("sn") String sn) throws Exception;
}
