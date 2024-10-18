package com.zzyl.service.impl;

import com.zzyl.mapper.NursingProjectPlanMapper;
import com.zzyl.service.NursingProjectPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Descriptioin NursingProjectPlanServiceImpl
 * @Author AvA
 * @Date 2024-10-18
 */
@Service
public class NursingProjectPlanServiceImpl implements NursingProjectPlanService {

    @Autowired
    private NursingProjectPlanMapper nursingProjectPlanMapper;

    /**
     * 根据护理计划删除关联关系
     * @param id
     */
    @Override
    public void deleteByNursingPlanId(Long id) {
        nursingProjectPlanMapper.deleteByNursingPlanId(id);
    }
}
