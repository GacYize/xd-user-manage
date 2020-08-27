package com.xdbigdata.user_manage_admin.feign;

import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.model.AdjustmentRecordDto;
import com.xdbigdata.user_manage_admin.model.dto.BedInfoDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "abtc-accommodation")
public interface AccommodationFeign {

    /**
     * 查询宿舍号
     *
     * @param sn 学号
     * @return
     */
    @RequestMapping(value = "/api/open/getCurrentCheckInfoBySn/{sn}", method = RequestMethod.GET)
    JsonResponse<String> getStudentDorm(@PathVariable("sn") String sn);

    /**
     * 查询入住信息
     *
     * @param sn 学号
     * @return
     */
    @RequestMapping(value = "/api/open/getBedInf/{sn}", method = RequestMethod.GET, headers = {"cfrom=thirdApplication"})
    JsonResponse getBedDetailBySn(@PathVariable("sn") String sn);

    /**
     * 查询宿舍分布区
     *
     * @param sn 学号
     * @return
     */
    @RequestMapping(value = "/api/open/getSchoolAreaBySn/{sn}", method = RequestMethod.GET)
    JsonResponse<String> getStudentDormitoryArea(@PathVariable("sn") String sn);

    /**
     * 查询学生所属校区，宿舍
     */
    @RequestMapping(value = "/api/open/getLastAdjust", method = RequestMethod.GET)
    JsonResponse<List<AdjustmentRecordDto>> getLastAdjust();
}
