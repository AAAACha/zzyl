package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.User;
import com.zzyl.mapper.PostMapper;
import com.zzyl.mapper.UserMapper;
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
    private final UserMapper userMapper;

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

    /**
     * 用户添加
     * @param userDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "用户添加")
    public ResponseResult<UserVo> addUser(@RequestBody UserDto userDto){
        UserVo userVo = userService.addUser(userDto);

        return ResponseResult.success(userVo);
    }

    /**
     * 用户修改
     * @param userDto
     * @return
     */
    @PatchMapping
    @ApiOperation(value = "用户修改")
    public ResponseResult<Boolean> updateUser(@RequestBody UserDto userDto){
        Boolean flag = userService.updateUser(userDto);
        return ResponseResult.success(flag);
    }

    /**
     * 启用或禁用用户
     * @param id
     * @param status
     * @return
     */
    @PutMapping("is-enable/{id}/{status}")
    @ApiOperation(value = "启用或禁用用户")
    public ResponseResult enableOrDisable(@PathVariable Long id, @PathVariable String status ){
        userService.enableOrDisable(id, status);
        return ResponseResult.success();
    }

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @DeleteMapping("/remove/{userId}")
    @ApiOperation("删除用户")
    public ResponseResult deleteUserById(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ResponseResult.success();
    }
}
