package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.zzyl.dto.LoginDto;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.service.LoginService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.vo.UserAuth;
import com.zzyl.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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

        HashMap<String, Object> clamis = new HashMap<>();
        clamis.put("currentUser", JSONUtil.toJsonStr(userVo));

        String jwtToken = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), clamis);
        userVo.setUserToken(jwtToken);

        return userVo;
    }
}
