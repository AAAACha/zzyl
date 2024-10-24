package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageSerializable;
import com.zzyl.base.PageResponse;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.RoleDto;
import com.zzyl.entity.Role;
import com.zzyl.entity.RoleResource;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.mapper.RoleResourceMapper;
import com.zzyl.mapper.UserRoleMapper;
import com.zzyl.service.RoleService;
import com.zzyl.utils.EmptyUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Descriptioin RoleServiceImpl
 * @Author AvA
 * @Date 2024-10-24
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

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

    /**
     * 根据角色查询选中的资源数据
     * @param roleId
     * @return
     */
    @Override
    public Set<String> findCheckedResources(Long roleId) {
        return roleResourceMapper.findCheckedResources(roleId);
    }

    @Override
    public void updateRole(RoleDto roleDto) {
        Role role = BeanUtil.toBean(roleDto, Role.class);

        if(SuperConstant.DATA_STATE_1.equals(role.getDataState())){
            Integer count = userRoleMapper.findCountByRoleId(role.getId());

            if(count > 0){
                throw new RuntimeException("该角色已分配用户,不能禁用");
            }
        }

        roleMapper.updateByPrimaryKeySelective(role);

        String[] checkedResourceNos = roleDto.getCheckedResourceNos();

        if(checkedResourceNos != null && checkedResourceNos.length > 0){
            roleResourceMapper.deleteByRoleId(role.getId());

            List<RoleResource> roleResourceList = Arrays.stream(checkedResourceNos).map(resourceNo ->{
                return RoleResource.builder()
                        .roleId(role.getId())
                        .resourceNo(resourceNo
                        ).dataState(SuperConstant.DATA_STATE_0)
                        .build();
            }).collect(Collectors.toList());
            roleResourceMapper.batchInsert(roleResourceList);
        } else if(checkedResourceNos != null){
            throw new RuntimeException("请勾选分配的资源");
        }
    }

    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @Transactional
    @Override
    public int deleteRoleById(Long roleId) {
        // 删除角色与菜单关联
        roleResourceMapper.deleteRoleResourceByRoleId(roleId);
        // 再删除角色数据
        return roleMapper.deleteByPrimaryKey(roleId);
    }

    /**
     * 角色下拉框
     * @return
     */
    @Override
    public List<RoleVo> initRoles() {
        List<Role> roleList = roleMapper.initRoles();
        return BeanUtil.copyToList(roleList, RoleVo.class);
    }
}
