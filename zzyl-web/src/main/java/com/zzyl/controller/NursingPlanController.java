package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Descriptioin NursingPlanController
 * @Author AvA
 * @Date 2024-10-17
 */
@RestController
@RequestMapping("/nursing/plan")
@Api(tags = "护理计划")
public class NursingPlanController {

    @Autowired
    private NursingPlanService nursingPlanService;

    /**
     * 查询所有护理计划
     * @return
     */
    @GetMapping("/all")
    @ApiOperation("查询所有护理项目")
    public ResponseResult<List<NursingPlanVo>> getAllNursingPlans(){
        List<NursingPlanVo> nursingPlanVoList = nursingPlanService.getAllNursingPlans();
        return ResponseResult.success(nursingPlanVoList);
    }

    /**
     * 护理计划条件分页查询
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("search")
    @ApiOperation("护理计划条件分页查询")
    public ResponseResult<PageResponse<NursingPlanVo>> page(
            @ApiParam(value = "护理计划名称") String name,
            @ApiParam(value = "状态:0-禁用, 1-启用") Integer status,
            @ApiParam(value = "当前页码")
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页显示数量")
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ){
        PageResponse<NursingPlanVo> nursingPlanPageInfo = nursingPlanService.page(name,status,pageNum,pageSize);
        return ResponseResult.success(nursingPlanPageInfo);
    }
}
