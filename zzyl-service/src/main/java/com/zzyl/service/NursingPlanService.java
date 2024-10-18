package com.zzyl.service;

import com.zzyl.vo.NursingPlanVo;

import java.util.List;

/**
 * @Descriptioin NursingPlanService
 * @Author AvA
 * @Date 2024-10-17
 */
public interface NursingPlanService {

    /**
     * 查询所有护理计划
     */
    List<NursingPlanVo> getAllNursingPlans();
}
