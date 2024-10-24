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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

        PageResponse<RoleVo> roleVoPage = roleService.findRoleVoPage(roleDto, pageNum, pageSize);

        return ResponseResult.success(roleVoPage);
    }
}
