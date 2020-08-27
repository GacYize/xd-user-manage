package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 助学金
 *
 * @author shuhan
 * @create 2019-02-21 10:27
 */
@FeignClient(value = "grants") //url = "172.16.170.92:8092"
public interface IGrantsService {


    /**
     *根据sn查询记录
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/grantsRemote/findStudentGrantsBySn/{sn}" ,method = RequestMethod.GET)
    JsonResponse findHaveGotGrants(@RequestParam("sn") String sn);
}
