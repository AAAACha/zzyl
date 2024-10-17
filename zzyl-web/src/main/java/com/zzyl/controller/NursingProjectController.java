package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Descriptioin NursingProjectController 护理项目
 * @Author AvA
 * @Date 2024-10-15
 */
@RestController
@RequestMapping("/nursing_project")
@Api(tags = "护理项目管理")
public class NursingProjectController {

    @Autowired
    private NursingProjectService nursingProjectService;

    /**
     * 护理项目分页查询
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping
    @ApiOperation("分页查询护理项目列表")
    public ResponseResult<PageResponse<NursingProjectVo>> getByPage(
            @ApiParam(value = "护理项目名称") String name,
            @ApiParam(value = "状态:0-禁用, 1-启用") Integer status,
            @ApiParam(value = "当前页码")
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页显示数量")
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        PageResponse<NursingProjectVo> nursingProjectPageInfo = nursingProjectService.getByPage(name, status, pageNum, pageSize);
        return ResponseResult.success(nursingProjectPageInfo);
    }

    /**
     * 新增护理项目
     * @param nursingProjectDto
     * @return
     */
    @PostMapping
    @ApiOperation("新增护理项目")
    public ResponseResult add(
            @ApiParam(value = "护理项目数据传输对象", required = true)
            @RequestBody NursingProjectDto nursingProjectDto
    ){
        nursingProjectService.add(nursingProjectDto);
        return ResponseResult.success();
    }
}
