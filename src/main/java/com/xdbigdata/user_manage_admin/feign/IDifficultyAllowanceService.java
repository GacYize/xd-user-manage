package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 困难补助
 *
 * @author shuhan
 * @create 2019-02-19 10:27
 */
@FeignClient(value = "difficultyallowance") // url = "172.16.170.82:8079
public interface IDifficultyAllowanceService {

    /**
     * 根据学生sn获取奖困难补助信息
     *
     * @return
     */
    @RequestMapping(value = "/api/Common/listStudentAllowanceBySn/{sn}", method = RequestMethod.GET)
    JsonResponse getStudentDifficultyAllowances(@PathVariable("sn") String sn);
}
