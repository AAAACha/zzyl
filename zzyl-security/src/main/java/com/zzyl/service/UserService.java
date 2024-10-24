package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.UserDto;
import com.zzyl.vo.UserVo;

import java.util.List;

/**
 * @Descriptioin UserService
 * @Author AvA
 * @Date 2024-10-24
 */
public interface UserService {
    /**
     * 用户分页查询
     * @param userDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<UserVo> userPage(UserDto userDto, int pageNum, int pageSize);

    UserVo addUser(UserDto userDto);

    Boolean updateUser(UserDto userDto);

    /**
     * 启用或禁用用户
     * @param id
     * @param status
     */
    void enableOrDisable(Long id, String status);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUserById(Long userId);

    /**
     * 用户列表
     * @param userDto
     * @return
     */
    List<UserVo> list(UserDto userDto);

    /**
     * 密码重置
     * @param userId
     */
    void resetPassword(Long userId);
}
