package com.zua.webConifg;

import com.zua.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
