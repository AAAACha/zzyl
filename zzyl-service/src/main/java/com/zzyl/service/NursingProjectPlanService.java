package com.zzyl.service;

import com.zzyl.dto.NursingProjectPlanDto;

import java.util.List;

/**
 * @Descriptioin NursingProjectPlanService
 * @Author AvA
 * @Date 2024-10-18
 */
public interface NursingProjectPlanService {

    /**
     * 根据护理计划删除关联关系
     * @param id
     */
    void deleteByNursingPlanId(Long id);


    int addList(List<NursingProjectPlanDto> projectPlans);
}
