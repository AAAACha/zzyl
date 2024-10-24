package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.User;
import com.zzyl.mapper.PostMapper;
import com.zzyl.service.UserService;
import com.zzyl.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.web.PathMappedEndpoints;
import org.springframework.web.bind.annotation.*;

/**
 * @Descriptioin UserController
 * @Author AvA
 * @Date 2024-10-24
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户分页查询
     * @param userDto
     * @param pageNum
     * @param PageSize
     * @return
     */
    @PostMapping("/page/{pageNum}/{PageSize}")
    @ApiOperation(value = "用户分页查询")
    public ResponseResult<PageResponse<UserVo>> userPage(
            @RequestBody UserDto userDto,
            @PathVariable int pageNum,
            @PathVariable int PageSize){

        PageResponse<UserVo> page =  userService.userPage(userDto, pageNum, PageSize);

        return ResponseResult.success(page);
    }

    @PutMapping
    @ApiOperation(value = "用户添加")
    public ResponseResult<UserVo> addUser(@RequestBody UserDto userDto){
        UserVo userVo = userService.addUser(userDto);

        return ResponseResult.success(userVo);
    }
}