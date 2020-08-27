package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.model.dto.api.RecordQuery;
import com.xdbigdata.user_manage_admin.model.dto.student.StudentScholarship;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "abtc-scholarship")
public interface ScholarshipFeign {

    @RequestMapping(value = "/scholarships/getScholarship/{sn}", method = RequestMethod.GET)
    JsonResponse<List<StudentScholarship>> getScholarship(@RequestParam("sn") String sn);

    /**
     *根据sn查询记录
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/applications/record" ,method = RequestMethod.POST)
    JsonResponse getAllLoanList(@RequestBody RecordQuery recordQuery);
}
