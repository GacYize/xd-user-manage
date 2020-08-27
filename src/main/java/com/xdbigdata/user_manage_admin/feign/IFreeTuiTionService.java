package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 *学费减免
 * @author shuhan
 * @create 2019-02-21 10:27
 */
@FeignClient(value = "free-tuition") //
public interface IFreeTuiTionService {

    /**
     *根据条件查询审核通过学费减免信息-根据学号
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/api/remote/findAllPassHistoryBySn/{sn}" ,method = RequestMethod.GET)
    JsonResponse findAllPassHistoryBySn(@RequestParam("sn") String sn);


}
