package com.zzyl.controller;


import cn.hutool.core.lang.tree.Tree;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.ResourceDto;
import com.zzyl.service.ResourceService;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资源前端控制器
 */
@Slf4j
@Api(tags = "资源管理")
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 资源列表查询
     * @param resourceDto
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "资源列表", notes = "资源列表")
    @ApiImplicitParam(name = "resourceDto", value = "资源DTO对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.parentResourceNo", "resourceDto.resourceType"})
    public ResponseResult<List<ResourceVo>> resourceList(@RequestBody ResourceDto resourceDto) {
        List<ResourceVo> resourceVoList = resourceService.findResourceList(resourceDto);

        return ResponseResult.success(resourceVoList);
    }

    @PostMapping("/tree")
    @ApiOperation(value = "资源树形", notes = "资源树形")
    @ApiImplicitParam(name = "resourceDto", value = "资源DTO对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.label"})
    public ResponseResult<TreeVo> resourceTreeVo(@RequestBody ResourceDto resourceDto){
        TreeVo treeVo = resourceService.resourceTreeVo(resourceDto);

        return ResponseResult.success(treeVo);
    }

    @PutMapping
    @ApiOperation(value = "资源添加", notes = "资源添加")
    @ApiImplicitParam(name = "resourceDto", value = "资源DTO对象", required = true, dataType = "ResourceDto")
    @ApiOperationSupport(includeParameters = {"resourceDto.dataState"
            , "resourceDto.icon"
            , "resourceDto.parentResourceNo"
            , "resourceDto.requestPath"
            , "resourceDto.resourceName"
            , "resourceDto.resourceType"
            , "resourceDto.sortNo"})
    public ResponseResult<Boolean> createResource(@RequestBody ResourceDto resourceDto){
        Boolean flag =  resourceService.createResource(resourceDto);
        return ResponseResult.success(flag);
    }

    @PostMapping("/enable")
    @ApiOperation(value = "启用禁用", notes = "启用/禁用")
    @ApiImplicitParam(name = "resourceVo", value = "资源Vo对象", required = true, dataType = "ResourceVo")
    @ApiOperationSupport(includeParameters = {"resourceVo.dataState",
            "resourceVo.parentResourceNo",
            "resourceVo.resourceNo"})
    public ResponseResult enableOrDisable(@RequestBody ResourceDto resourceDto){
        resourceService.enableOrDisable(resourceDto);

        return ResponseResult.success();
    }
}