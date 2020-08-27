package com.xdbigdata.user_manage_admin.util;

import com.xdbigdata.user_manage_admin.mapper.GradeMapper;
import com.xdbigdata.user_manage_admin.mapper.OrganizationMapper;
import com.xdbigdata.user_manage_admin.model.GradeModel;
import com.xdbigdata.user_manage_admin.model.OrganizationModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 组织机构工具类
 */
@Component
public class OrganizationUtil {

    /**
     * 组织机构redis中缓存的key
     */
    public static final String ORGANIZATION_CACHE_KEY = OrganizationUtil.class.getName() + ":organization";
    /**
     * 年级redis中缓存的key
     */
    public static final String GRADE_CACHE_KEY = OrganizationUtil.class.getName() + ":grade";

    private static StringRedisTemplate redisTemplate;

    private static GradeMapper gradeMapper;

    private static OrganizationMapper organizationMapper;

    @Autowired
    public void setGradeMapper(GradeMapper gradeMapper) {
        OrganizationUtil.gradeMapper = gradeMapper;
    }

    @Autowired
    public void setOrganizationMapper(OrganizationMapper organizationMapper) {
        OrganizationUtil.organizationMapper = organizationMapper;
    }

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        OrganizationUtil.redisTemplate = redisTemplate;
    }

    /**
     * 根据组织机构code获取对应名称
     *
     * @param code 组织机构code
     * @return 组织机构code对应的名称
     */
    public static String getName(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        // 从redis中获取组织机构
        Long size = redisTemplate.opsForHash().size(ORGANIZATION_CACHE_KEY);
        if (size == null || size == 0) {
            Map<String, String> organizationMap = organizationMapper.selectAll()
                    .stream()
                    .filter(o -> StringUtils.isNotBlank(o.getSn()))
                    .collect(Collectors.toMap(OrganizationModel::getSn, OrganizationModel::getName, (k1, k2) -> k1));
            redisTemplate.opsForHash().putAll(ORGANIZATION_CACHE_KEY, organizationMap);
            redisTemplate.expire(ORGANIZATION_CACHE_KEY, 1, TimeUnit.DAYS);
        }
        return (String) redisTemplate.opsForHash().get(ORGANIZATION_CACHE_KEY, code);
    }

    /**
     * 根据年级id获取对应名称
     *
     * @param id 年级id
     * @return 年级id对应的名称
     */
    public static String getGrade(Long id) {
        if (Objects.isNull(id)) {
            return null;
        }
        // 从redis中获取年级
        Long size = redisTemplate.opsForHash().size(GRADE_CACHE_KEY);
        if (size == null || size == 0) {
            Map<String, String> gradeMap = gradeMapper.selectAll()
                    .stream()
                    .collect(Collectors.toMap(g -> g.getId().toString(), GradeModel::getName, (k1, k2) -> k1));
            redisTemplate.opsForHash().putAll(GRADE_CACHE_KEY, gradeMap);
            redisTemplate.expire(GRADE_CACHE_KEY, 1, TimeUnit.DAYS);
        }
        return (String) redisTemplate.opsForHash().get(GRADE_CACHE_KEY, id.toString());
    }

}
