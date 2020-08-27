package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 无息借款
 *
 * @author shuhan
 * @create 2019-03-08 10:27
 */
@FeignClient(value = "borrow") // url = "172.16.170.92:8082"
public interface IBorrowService {
    /**
     * 根据sn获取无息借款信息
     *
     * @return
     */
    @RequestMapping(value = "/common/getLoanListBySn/{sn}", method = RequestMethod.GET)
    JsonResponse getLoanListBySn(@RequestParam("sn") String sn);


}
