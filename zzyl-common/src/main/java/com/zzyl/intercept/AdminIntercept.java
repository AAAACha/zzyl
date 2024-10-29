package com.zzyl.intercept;

import com.zzyl.constant.Constants;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.utils.EmptyUtil;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 多租户放到SubjectContent上下文中
 */
@Component
public class AdminIntercept implements HandlerInterceptor {

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //从头部中拿到当前userToken
        String token = request.getHeader(Constants.USER_TOKEN);
        if (!EmptyUtil.isNullOrEmpty(token)) {
            Object userObj = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), token).get("currentUser");
            String currentUser = String.valueOf(userObj);
            //放入当前线程中：用户当前的web直接获得user使用
            UserThreadLocal.setSubject(currentUser);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //移除当前线程中的参数
        UserThreadLocal.removeSubject();
    }
}