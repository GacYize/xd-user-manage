package com.xdbigdata.user_manage_admin.model.dto.api;

import com.xdbigdata.framework.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huyuanjia
 * @date 2018/9/18 16:49
 */
@Slf4j
public class CommonUtil {

    /**
     * 实体类转map
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                log.error("objectToMap failed" + e);
            }
        }
        return map;
    }

    /**
     * map转实体
     *
     * @param map
     * @param beanClass
     * @return
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
        if (map == null)
            return null;
        Object obj = null;
        try {

            obj = beanClass.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }

                field.setAccessible(true);
                if (map.get(field.getName()) == null) {
                    continue;
                }
                field.set(obj, map.get(field.getName()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("mapToObject error: " + e);
            throw new BaseException("系统错误");
        }


        return obj;
    }


    /**
     * 去除字符串空白（\s 可以匹配空格、制表符、换页符等空白字符）
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if (str == null) {
            return str;
        }
        return str.replaceAll("\\s*", "");
    }


    /**
     * 时间字符串yyyy-MM-dd=日历时间+月数
     *
     * @param cal
     * @param month
     * @return
     */
    public static String getDateStr(Calendar cal, Integer month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.add(Calendar.MONTH, month);
        return sdf.format(cal.getTime());
    }

    /**
     * 根据ms获取时间字符串
     *
     * @param time
     * @return
     */
    public static String getDateStr(Long time) {
        if (time == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    public static String getDateAllStr(Long time) {
        if (time == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(calendar.getTime());
    }

    /**
     * 去除日期字符串多余的小数点
     *
     * @param dateStr
     * @return
     */
    public static String formatDateStr(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        if (dateStr.lastIndexOf(".") > 0) {

            if (dateStr.length() > 5) {
                return dateStr.substring(0, dateStr.length() - 5);
            }
        } else {
            if (dateStr.length() > 3) {
                return dateStr.substring(0, dateStr.length() - 3);
            }
        }
        return dateStr;
    }


    public static void main(String[] args) {
        String str = "2018-1-1 00:00:00";
        System.out.println(formatDateStr(str));
    }

    /**
     * 将list转字符串数组
     *
     * @param list
     * @return
     */
    public static String list2Str(List list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return com.alibaba.fastjson.JSONObject.toJSONString(list);
    }
}
