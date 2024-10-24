package com.zzyl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.RoleDto;
import com.zzyl.service.RoleService;
import com.zzyl.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @Descriptioin RoleController
 * @Author AvA
 * @Date 2024-10-24
 */
@Slf4j
@Api(tags = "角色管理")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /**
     * 角色分页查询
     * @param roleDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @PostMapping("/page/{pageNum}/{pageSize}")
    @ApiOperation(value = "角色分页", notes = "角色分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "roleDto"),
            @ApiImplicitParam(paramType = "path",name = "pageNum",value = "页码",example = "1",dataType = "Integer"),
            @ApiImplicitParam(paramType = "path",name = "pageSize",value = "每页条数",example = "10",dataType = "Integer")
    })
    @ApiOperationSupport(includeParameters = {"roleDto.roleName"})
    public ResponseResult<PageResponse<RoleVo>> findRoleVoPage(
            @RequestBody RoleDto roleDto,
            @PathVariable int pageNum,
            @PathVariable int pageSize){

        log.info("角色分页查询, 请求参数: roleDto = #{}, pageNum = #{}, pageSize = #{}",roleDto, pageNum, pageSize);

        PageResponse<RoleVo> roleVoPage = roleService.findRoleVoPage(roleDto, pageNum, pageSize);

        return ResponseResult.success(roleVoPage);
    }

    /**
     * 角色添加
     * @param roleDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "角色添加", notes = "角色添加")
    @ApiImplicitParam(name = "roleDto", value = "roleDto", required = true, dataType = "roleDto")
    @ApiOperationSupport(includeParameters = {"roleDto.roleName", "roleDto.dataState"})
    public ResponseResult addRole(@RequestBody RoleDto roleDto){

        log.info("角色添加, 请求参数: roleDto = #{}",roleDto);

        roleService.addRole(roleDto);

        return ResponseResult.success();
    }

    /**
     * 根据角色查询选中的资源数据
     * @param roleId
     * @return
     */
    @GetMapping("/find-checked-resources/{roleId}")
    @ApiOperation(value = "根据角色查询选中的资源数据", notes = "根据角色查询选中的资源数据")
    public ResponseResult<Set<String>> findCheckedResources (@PathVariable Long roleId){
        log.info("根据角色查询选中的资源数据, 请求参数: roleId = #{}",roleId);

        Set<String> resouceNo =  roleService.findCheckedResources(roleId);

        return ResponseResult.success(resouceNo);
    }

    @PatchMapping
    @ApiOperation(value = "角色修改")
    @ApiImplicitParam(name = "roleDto",value = "角色DTO对象",required = true,dataType = "roleDto")
    @ApiOperationSupport(includeParameters = {"roleDto.roleName","roleDto.dataState","roleDto.dataScope","roleDto.checkedResourceNos","roleDto.checkedDeptNos","roleDto.id"})
    public ResponseResult<Boolean> updateRole(@RequestBody RoleDto roleDto){
       roleService.updateRole(roleDto);
       return ResponseResult.success(true);
    }

    /**
     * 删除角色
     */
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{roleId}")
    public ResponseResult remove(@PathVariable("roleId") Long roleId) {
        return ResponseResult.success(roleService.deleteRoleById(roleId));
    }

    /**
     * 角色下拉框
     * @return
     */
    @PostMapping("/init-roles")
    @ApiOperation(value = "角色下拉框")
    public ResponseResult<List<RoleVo>> initRoles(){

        List<RoleVo> roleVoList = roleService.initRoles();

        return ResponseResult.success(roleVoList);
    }
}
