package com.xdbigdata.user_manage_admin.web.controller;

import com.xdbigdata.framework.web.model.JsonResponse;
import com.xdbigdata.user_manage_admin.model.OriginPlaceModel;
import com.xdbigdata.user_manage_admin.model.domain.Dictionary;
import com.xdbigdata.user_manage_admin.service.DictionaryService;
import com.xdbigdata.user_manage_admin.service.OriginPlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/dictionary")
@Api(value = "数据字典控制层", tags = "数据字典")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private OriginPlaceService originPlaceService;

    @GetMapping("/getAll")
    @ApiOperation("获取所有的数据字典下拉信息")
    public JsonResponse<Map<String, List<Dictionary>>> getAll() {
        Map<String, List<Dictionary>> data =  dictionaryService.getAll();
        return JsonResponse.success(data);
    }

    @GetMapping("/getAllSourcePlace")
    @ApiOperation("获取生源地下拉信息")
    public JsonResponse<List<OriginPlaceModel>> getAllSourcePlace() {
        List<OriginPlaceModel> data = originPlaceService.getByParentCode("1");
        return JsonResponse.success(data);
    }


}