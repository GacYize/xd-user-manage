package com.xdbigdata.user_manage_admin.annotation;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE })
@Inherited
/**
 * @创建人:huangjianfeng
 * @简要描述:日志注解
 * @创建时间: 2018/10/25 10:43
 * @参数:
 * @返回:
 */
public @interface OperateLog {

    String module() default "";


}
