package com.xdbigdata.user_manage_admin.model.dto.api;


import lombok.extern.slf4j.Slf4j;

/**
 * 参数校验
 *
 * @author huyuanjia
 */
@Slf4j
public class CheckParamsUtil {
    /**
     * string判空
     *
     * @param params
     * @return
     */
    public static boolean check(String... params) {
        for (String param : params) {
            if (param == null
                    || param.replaceAll("\\s*", "").equals("")
                    || param.equalsIgnoreCase("null")
                    || param.equalsIgnoreCase("undefined")) {
                return false;
            }
        }
        return true;
    }

}
