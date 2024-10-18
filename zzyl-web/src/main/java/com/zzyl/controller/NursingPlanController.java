package com.zzyl.controller;

import com.zzyl.base.ResponseResult;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
