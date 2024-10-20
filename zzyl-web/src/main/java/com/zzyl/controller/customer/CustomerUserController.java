package com.zzyl.controller.customer;

import com.zzyl.base.ResponseResult;
import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.service.MemberService;
import com.zzyl.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Descriptioin CustomerUserController
 * @Author AvA
 * @Date 2024-10-20
 */
@Slf4j
@Api(tags = "客户管理")
@RestController
@RequestMapping("/customer/user")
public class CustomerUserController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseResult<LoginVo> login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        LoginVo loginVo = memberService.login(userLoginRequestDto);
        return ResponseResult.success(loginVo);
    }
}
