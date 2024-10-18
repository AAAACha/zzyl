package com.zzyl.service;

import com.zzyl.base.PageResponse;
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

    /**
     *护理计划条件分页查询
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<NursingPlanVo> page(String name, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 根据id查询护理计划
     * @param id
     * @return
     */
    NursingPlanVo getById(Long id);

    /**
     * 根据id删除护理计划
     * @param id
     */
    void deleteById(Long id);
}
