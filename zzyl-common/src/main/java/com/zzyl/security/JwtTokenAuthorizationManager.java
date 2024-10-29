package com.zzyl.security;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.json.JSONUtil;
import com.zzyl.constant.Constants;
import com.zzyl.constant.UserCacheConstant;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.StringUtils;
import com.zzyl.vo.UserVo;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author sjqn
 */
@Component
public class JwtTokenAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {

        //获取token
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String token = request.getHeader(Constants.USER_TOKEN);
        //判断token是否为空
        if(ObjectUtil.isEmpty(token)){
            return new AuthorizationDecision(false);
        }
        //解析数据
        Claims claims = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), token);
        if(ObjectUtil.isEmpty(claims)){
            return new AuthorizationDecision(false);
        }
        //获取用户数据
        UserVo userVo = JSONUtil.toBean(String.valueOf(claims.get("currentUser")), UserVo.class);

        //从缓存中获取url列表
        String accessUrlsCacheKey = UserCacheConstant.ACCESS_URLS_CACHE+userVo.getId();
        String accessUrlsJson = redisTemplate.opsForValue().get(accessUrlsCacheKey);
        List<String> accessUrls = null;
        if(StringUtils.isNotEmpty(accessUrlsJson)){
            accessUrls = JSONUtil.toList(accessUrlsJson, String.class);
        }

        //当前请求路径： GET/nursing_project/**
        String targetUrl = request.getMethod() + request.getRequestURI();

        //验证用户是否拥有该资源
        for (String url : accessUrls) {
            if(antPathMatcher.match(url, targetUrl)){
                return new AuthorizationDecision(true);
            }
        }
        //构建为false，会自动跳转为403错误
        return new AuthorizationDecision(false);
    }
}