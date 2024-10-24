package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zzyl.base.PageResponse;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.User;
import com.zzyl.entity.UserRole;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.DeptMapper;
import com.zzyl.mapper.RoleMapper;
import com.zzyl.mapper.UserMapper;
import com.zzyl.mapper.UserRoleMapper;
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
    private final DeptMapper deptMapper;
    private final UserRoleMapper userRoleMapper;
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

    @Override
    public UserVo addUser(UserDto userDto) {
        if(!isLowestDept(userDto.getDeptNo())){
            throw new BaseException(BasicEnum.USER_LOCATED_BOTTOMED_DEPT);
        }

        User user = BeanUtil.toBean(userDto, User.class);
        user.setUsername(user.getEmail());
        user.setNickName(user.getRealName());
        user.setDataState(SuperConstant.DATA_STATE_0);
        user.setPassword("123456");

        int flag = userMapper.insertSelective(user);

        if(flag == 0){
            throw new RuntimeException("保存用户信息出错");
        }

        List<UserRole> userRoles = Lists.newArrayList();
        userDto.getRoleVoIds().forEach(r ->{
            userRoles.add(UserRole.builder()
                    .userId(user.getId())
                    .roleId(Long.valueOf(r))
                    .dataState(SuperConstant.DATA_STATE_0)
                    .build());
        });
        flag =  userRoleMapper.batchInsert(userRoles);
        if(flag == 0){
            throw new RuntimeException("保存用户角色中间表出错");
        }
        return BeanUtil.toBean(user, UserVo.class);
    }

    private boolean isLowestDept(String deptNo){
        int count = deptMapper.isLowestDept(deptNo);
        if(count > 0){
            return false;
        }
        return true;
    }
}
