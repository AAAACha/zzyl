package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zzyl.constant.SuperConstant;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.UserMapper;
import com.zzyl.entity.User;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.UserAuth;
import com.zzyl.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Descriptioin UserDetailsServiceImpl
 * @Author AvA
 * @Date 2024-10-29
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVo userVo = userMapper.findUserVoForLogin(username);

        if(ObjectUtil.isEmpty(userVo)){
            throw new BaseException(BasicEnum.LOGIN_FAIL);
        }

        if(SuperConstant.DATA_STATE_1.equals(userVo.getDataState())){
            throw new RuntimeException("用户已停用,无法登录");
        }

        return BeanUtil.toBean(userVo, UserAuth.class);
    }
}
