package com.zzyl.service;

import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.vo.LoginVo;

/**
 * @Descriptioin MemberService
 * @Author AvA
 * @Date 2024-10-20
 */
public interface MemberService {

    LoginVo login(UserLoginRequestDto userLoginRequestDto);
}
