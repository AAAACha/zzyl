package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.UserDto;
import com.zzyl.vo.UserVo;

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
}
