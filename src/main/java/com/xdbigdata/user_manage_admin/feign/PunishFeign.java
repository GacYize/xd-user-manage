package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.model.dto.api.PunishStudentQo;
import com.xdbigdata.user_manage_admin.model.dto.student.PunishStudentDetailVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "abtc-punish")
public interface PunishFeign {

    @RequestMapping(value = "/api/punish/getStudentPunishInf/{sn}", method = RequestMethod.GET)
    JsonResponse<List<PunishStudentDetailVo>> getPunishInfBySn(@RequestParam("sn") String sn);


    @RequestMapping(value = "/api/punish/listRecord", method = RequestMethod.GET)
    JsonResponse getPunishInfBySn(@RequestBody PunishStudentQo punishStudentQo);
}
