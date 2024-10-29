package com.zzyl.service;

import com.zzyl.dto.LoginDto;
import com.zzyl.vo.UserVo;

/**
 * @Descriptioin LoginService
 * @Author AvA
 * @Date 2024-10-29
 */
public interface LoginService {

    UserVo login(LoginDto loginDto);
}
