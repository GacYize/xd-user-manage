package com.xdbigdata.user_manage_admin.web.interceptor;

import com.xdbigdata.framework.web.interceptor.AbstractLoginInterceptor;

import javax.servlet.http.HttpServletRequest;

public class LoginInterceptor extends AbstractLoginInterceptor {

    @Override
    protected boolean isAjaxRequest(HttpServletRequest request) {
        return request.getHeader("x-requested-with") != null &&
                request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
    }
}
