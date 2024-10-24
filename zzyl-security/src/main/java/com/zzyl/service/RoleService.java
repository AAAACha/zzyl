package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.RoleDto;
import com.zzyl.vo.RoleVo;

/**
 * @Descriptioin RoleService
 * @Author AvA
 * @Date 2024-10-24
 */
public interface RoleService {
    /**
     * 角色分页查询
     * @param roleDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<RoleVo> findRoleVoPage(RoleDto roleDto, int pageNum, int pageSize);

    /**
     * 角色添加
     * @param roleDto
     */
    void addRole(RoleDto roleDto);
}
