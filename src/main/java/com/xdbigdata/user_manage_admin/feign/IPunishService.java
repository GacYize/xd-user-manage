package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.model.dto.api.PunishStudentQo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 违纪处分
 *
 * @author shuhan
 * @create 2019-03-08 10:27
 */
@FeignClient(value = "abtc-punish")
public interface IPunishService {
    /**
     * 根据sn获取无息借款信息
     *
     * @return
     */
    @RequestMapping(value = "/api/punish/listRecord", method = RequestMethod.POST)
    JsonResponse getLoanListBySn(@RequestBody PunishStudentQo punishStudentQo);


}
