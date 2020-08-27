package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.model.dto.api.RecordQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 资助汇总
 *
 * @author shuhan
 * @create 2019-03-08 10:27
 */
@FeignClient(value = "abtc-scholarship")
public interface IScholarshipService {


}
