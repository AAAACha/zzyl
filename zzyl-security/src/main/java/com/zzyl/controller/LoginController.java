package com.zzyl.controller;

import com.zzyl.base.ResponseResult;
import com.zzyl.dto.LoginDto;
import com.zzyl.service.LoginService;
import com.zzyl.vo.UserVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Descriptioin LoginController
 * @Author AvA
 * @Date 2024-10-29
 */
@RestController
@Api(tags = "用户登录")
@RequestMapping("/security")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult<UserVo> login(@RequestBody LoginDto loginDto){
        UserVo userVo = loginService.login(loginDto);
        return ResponseResult.success(userVo);
    }
}
