package com.zzyl.service;

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
}
