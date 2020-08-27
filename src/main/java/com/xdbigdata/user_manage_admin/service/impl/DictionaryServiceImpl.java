package com.xdbigdata.user_manage_admin.service.impl;

import com.xdbigdata.user_manage_admin.mapper.DictionaryMapper;
import com.xdbigdata.user_manage_admin.model.domain.Dictionary;
import com.xdbigdata.user_manage_admin.service.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@CacheConfig(cacheNames = "Dictionary")
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
//    @Cacheable(key = "#root.targetClass + '_' + #root.methodName", unless = "#result eq null")
    public Map<String, List<Dictionary>> getAll() {
        Map<String, List<Dictionary>> map = dictionaryMapper.selectAllWithTypeName()
                .stream()
                .collect(Collectors.groupingBy(Dictionary::getTypeName));
        map.remove(6L);
        return map;
    }
}