package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Sets;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.User;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.mapper.UserMapper;
import com.zzyl.service.UserService;
import com.zzyl.utils.EmptyUtil;
import com.zzyl.utils.NoProcessing;
import com.zzyl.vo.RoleVo;
import com.zzyl.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Descriptioin UserServiceImpl
 * @Author AvA
 * @Date 2024-10-24
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    @Override
    public PageResponse<UserVo> userPage(UserDto userDto, int pageNum, int pageSize) {
        if (!EmptyUtil.isNullOrEmpty(userDto.getDeptNo())) {
            //截取部门编号  100001  dept_no like '100001%'
            userDto.setDeptNo(NoProcessing.processString(userDto.getDeptNo()));
        }

        PageHelper.startPage(pageNum, pageSize);

        Page<User> page = userMapper.userPage(userDto);
        PageResponse<UserVo> pageResponse = PageResponse.of(page, UserVo.class);
        if (!EmptyUtil.isNullOrEmpty(pageResponse.getRecords())) {
            List<Long> userIds = pageResponse.getRecords().stream().map(UserVo::getId).collect(Collectors.toList());
            //查询对应角色
            List<RoleVo> roleVoList = roleMapper.findRoleVoListInUserId(userIds);
            //装配数据
            pageResponse.getRecords().forEach(n -> {
                //装配角色
                Set<String> roleVoIds = Sets.newHashSet();
                Set<String> roleVoLabels = Sets.newHashSet();
                roleVoList.forEach(r -> {
                    if (String.valueOf(n.getId()).equals(r.getUserId())) {
                        roleVoIds.add(String.valueOf(r.getId()));
                        roleVoLabels.add(r.getRoleName());
                    }
                });
                n.setRoleLabels(roleVoLabels);
                n.setRoleVoIds(roleVoIds);
            });
        }
        return pageResponse;
    }
}
