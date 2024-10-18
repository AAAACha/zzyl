package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
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
    @GetMapping("/search")
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

    /**
     * 根据id查询护理计划
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询护理计划")
    public ResponseResult<NursingPlanVo> getById(@PathVariable("id") Long id){
        NursingPlanVo nursingPlanVo = nursingPlanService.getById(id);
        return ResponseResult.success(nursingPlanVo);
    }

    /**
     * 根据id删除护理计划
     * @param id
     */
    @DeleteMapping("/{id}")
    @ApiOperation("根据id删除护理计划")
    public ResponseResult deleteById(@PathVariable("id") Long id){
        nursingPlanService.deleteById(id);
        return ResponseResult.success();
    }

    /**
     * 添加护理计划
     * @param nursingPlanDto
     * @return
     */
    @PostMapping
    @ApiOperation("添加护理计划")
    public ResponseResult addNursingPlan(@RequestBody NursingPlanDto nursingPlanDto){
        nursingPlanService.add(nursingPlanDto);
        return ResponseResult.success();
    }
}
