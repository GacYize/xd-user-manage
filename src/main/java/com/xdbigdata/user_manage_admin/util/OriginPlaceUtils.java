package com.xdbigdata.user_manage_admin.util;

import com.alibaba.fastjson.JSON;
import com.xdbigdata.user_manage_admin.mapper.OriginPlaceMapper;
import com.xdbigdata.user_manage_admin.model.OriginPlaceModel;
import com.xdbigdata.user_manage_admin.model.vo.OriginPlaceVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class OriginPlaceUtils {

    public static final String ORIGIN_PLACE_CACHE_KEY = OriginPlaceUtils.class.getName() + ":cache";

    public static final String ORIGIN_PLACE_CACHE_ALL_KEY = OriginPlaceUtils.class.getName() + ":cacheAll";

    private static StringRedisTemplate redisTemplate;
    private static OriginPlaceMapper originPlaceMapper;

    @Autowired
    public void setOriginPlaceMapper(OriginPlaceMapper originPlaceMapper) {
        OriginPlaceUtils.originPlaceMapper = originPlaceMapper;
    }

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        OriginPlaceUtils.redisTemplate = redisTemplate;
    }

    /**
     * 根据省市区编码获取名称
     *
     * @param codes 省市区编码
     * @return 省市区编码对应的名称
     */
    public static String getName(String codes) {
        if (StringUtils.isBlank(codes)) {
            return null;
        }
        Long size = redisTemplate.opsForHash().size(ORIGIN_PLACE_CACHE_KEY);
        if (size == null || size == 0) {
            Map<String, String> originPlaceMap = originPlaceMapper.selectAll()
                    .stream()
                    .collect(Collectors.toMap(OriginPlaceModel::getCode, OriginPlaceModel::getName, (k1, k2) -> k1));
            redisTemplate.opsForHash().putAll(ORIGIN_PLACE_CACHE_KEY, originPlaceMap);
            redisTemplate.expire(ORIGIN_PLACE_CACHE_KEY, 1, TimeUnit.DAYS);
        }
        return Arrays.stream(codes.split(":"))
                .map(c -> redisTemplate.opsForHash().get(ORIGIN_PLACE_CACHE_KEY, c))
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(Collectors.joining(" "));

    }

    /**
     * 获取所有的行政区
     *
     * @return 所有的行政区集合
     */
    public static List<OriginPlaceVo> getAllOriginPlace() {
        String originPlaceCache = redisTemplate.opsForValue().get(ORIGIN_PLACE_CACHE_ALL_KEY);
        if (StringUtils.isNotBlank(originPlaceCache)) {
            return JSON.parseArray(originPlaceCache, OriginPlaceVo.class);
        }
        Map<String, List<OriginPlaceVo>> originPlaceMap = originPlaceMapper.selectAll()
                .stream()
                .map(m -> {
                    OriginPlaceVo vo = new OriginPlaceVo();
                    vo.setValue(m.getCode());
                    vo.setLabel(m.getName());
                    vo.setParentCode(m.getParentCode());
                    return vo;
                }).collect(Collectors.groupingBy(OriginPlaceVo::getParentCode));

        List<OriginPlaceVo> originPlaceVos = originPlaceMap.get("1");
        setChildren(originPlaceVos, originPlaceMap);
        originPlaceCache = JSON.toJSONString(originPlaceVos);
        redisTemplate.opsForValue().set(ORIGIN_PLACE_CACHE_ALL_KEY, originPlaceCache);
        redisTemplate.expire(ORIGIN_PLACE_CACHE_ALL_KEY, 1, TimeUnit.DAYS);
        return originPlaceVos;
    }

    private static void setChildren(List<OriginPlaceVo> parent, Map<String, List<OriginPlaceVo>> originPlaceMap) {
        if (CollectionUtils.isEmpty(parent)) {
            return;
        }
        parent.stream().forEach(p -> {
            List<OriginPlaceVo> children = originPlaceMap.get(p.getValue());
            p.setChildren(children);
            setChildren(children, originPlaceMap);
        });
    }
}
