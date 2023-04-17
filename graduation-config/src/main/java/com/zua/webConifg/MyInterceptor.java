package com.zua.webConifg;

import com.zua.annotation.Auth;
import com.zua.self.SelfException;
import com.zua.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Auth annotation;
        if(handler instanceof HandlerMethod){
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
        }else{
            return true;
        }
        if(annotation == null){
            return true;
        }

        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }
        if(StringUtils.isEmpty(token)){
            throw new SelfException(600,"token不能为空!");
        }

        if (StringUtils.isEmpty(token)) {
            throw new SelfException(600,"重新登入!");
        }
        Claims claimsFromToken = jwtUtils.getClaimsFromToken(token);
        if (claimsFromToken == null) {
            throw new SelfException(600,"重新登入!");
        }
        return true;
    }
}
