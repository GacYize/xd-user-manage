package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 困难认定
 *
 * @author shuhan
 * @create 2019-02-19 10:27
 */
//@FeignClient(value = "difficulties-identified",url = "172.16.170.82:8104")
@FeignClient(value = "difficulties-identified")
public interface IDifficultiesIdentifiedService {

    /**
     * 根据学生sn获取困难认定信息
     *
     * @return
     */
    @RequestMapping(value = "/external/findResultByStudentSnAndYear/{sn}/{yearName}", method = RequestMethod.GET)
    JsonResponse getStudentDifficultyAllowances(@PathVariable("sn") String sn, @PathVariable("yearName") String yearName);

    /**
     * 根据学生sn所有获取困难认定信息
     *
     * @return
     */
    @RequestMapping(value = "/external/findResultByStudentSnAll/{sn}", method = RequestMethod.GET)
    JsonResponse findResultByStudentSnAll(@PathVariable("sn") String sn);
}
