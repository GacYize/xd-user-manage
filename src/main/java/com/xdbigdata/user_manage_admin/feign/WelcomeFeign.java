package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "abtc-welcome")
public interface WelcomeFeign {

    @RequestMapping(path = "/home/reserveRegisterInfo/getDisabledInfBySn", method = RequestMethod.POST)
    JsonResponse getDisabledInfBySn(String sns);
}
