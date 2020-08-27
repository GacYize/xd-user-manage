package com.xdbigdata.user_manage_admin.util;

import com.xdbigdata.user_manage_admin.mapper.DictionaryMapper;
import com.xdbigdata.user_manage_admin.model.OriginPlaceModel;
import com.xdbigdata.user_manage_admin.model.domain.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 数据字典工具类
 */
@Component
public class DictionaryUtil {

    /**
     * 数据字典redis中缓存的key
     */
    public static final String DICTIONARY_CACHE_KEY = DictionaryUtil.class.getName() + ":cache";

    private static StringRedisTemplate redisTemplate;

    private static DictionaryMapper dictionaryMapper;

    @Autowired
    public void setDictionaryMapper(DictionaryMapper dictionaryMapper) {
        DictionaryUtil.dictionaryMapper = dictionaryMapper;
    }

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        DictionaryUtil.redisTemplate = redisTemplate;
    }

    /**
     * 根据数据字典id获取对应名称
     *
     * @param id 数据字典id
     * @return 数据字典对应的名称
     */
    public static String getName(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        // 从redis中获取数据字典
        Long size = redisTemplate.opsForHash().size(DICTIONARY_CACHE_KEY);
        if (size == null || size == 0) {
            Map<String, String> dictionaryMap = dictionaryMapper.selectAll()
                    .stream()
                    .collect(Collectors.toMap(d -> d.getId().toString(), Dictionary::getName, (k1, k2) -> k1));
            redisTemplate.opsForHash().putAll(DICTIONARY_CACHE_KEY, dictionaryMap);
            redisTemplate.expire(DICTIONARY_CACHE_KEY, 1, TimeUnit.DAYS);
        }
        return (String) redisTemplate.opsForHash().get(DICTIONARY_CACHE_KEY, id.toString());
    }
}
