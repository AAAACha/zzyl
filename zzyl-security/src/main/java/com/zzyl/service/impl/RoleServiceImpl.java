package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.RoleDto;
import com.zzyl.entity.Role;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.service.RoleService;
import com.zzyl.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Descriptioin RoleServiceImpl
 * @Author AvA
 * @Date 2024-10-24
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 角色分页查询
     * @param roleDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResponse<RoleVo> findRoleVoPage(RoleDto roleDto, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Page<RoleVo> page = roleMapper.findRoleVoPage(roleDto);

        return PageResponse.of(page,RoleVo.class);
    }

    /**
     * 角色添加
     * @param roleDto
     */
    @Override
    public void addRole(RoleDto roleDto) {
        Role role = BeanUtil.toBean(roleDto, Role.class);

        roleMapper.insertSelective(role);
    }
}
