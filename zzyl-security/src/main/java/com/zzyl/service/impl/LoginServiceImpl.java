package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.zzyl.constant.UserCacheConstant;
import com.zzyl.dto.LoginDto;
import com.zzyl.entity.Resource;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.properties.SecurityConfigProperties;
import com.zzyl.service.LoginService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.vo.UserAuth;
import com.zzyl.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Descriptioin LoginServiceImpl
 * @Author AvA
 * @Date 2024-10-29
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private SecurityConfigProperties securityConfigProperties;


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public UserVo login(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(upat);
        if(!authenticate.isAuthenticated()){
            throw new BaseException(BasicEnum.LOGIN_FAIL);
        }

        UserAuth userAuth = (UserAuth) authenticate.getPrincipal();
        UserVo userVo = BeanUtil.toBean(userAuth, UserVo.class);

        userVo.setPassword("");

        List<Resource> resourceList = resourceMapper.findResourceVoListByUserId(userVo.getId());
        List<String> resourcePath = resourceList.stream().map(Resource::getRequestPath).collect(Collectors.toList());

        //获取白名单列表
        List<String> publicAccessUrls = securityConfigProperties.getPublicAccessUrls();
        //合并列表
        resourcePath.addAll(publicAccessUrls);


        HashMap<String, Object> clamis = new HashMap<>();
        clamis.put("currentUser", JSONUtil.toJsonStr(userVo));

        String jwtToken = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), clamis);
        userVo.setUserToken(jwtToken);

        long ttl = Long.valueOf(jwtTokenManagerProperties.getTtl() / 1000);
        String accessUrlsCacheKey = UserCacheConstant.ACCESS_URLS_CACHE+userVo.getId();
        redisTemplate.opsForValue().set(accessUrlsCacheKey,JSONUtil.toJsonStr(resourcePath),ttl, TimeUnit.SECONDS);

        return userVo;
    }
}
