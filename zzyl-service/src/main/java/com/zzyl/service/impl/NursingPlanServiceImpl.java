package com.zzyl.service.impl;

import com.zzyl.mapper.NursingPlanMapper;
import com.zzyl.service.NursingPlanService;
import com.zzyl.vo.NursingPlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descriptioin NursingPlanServiceImpl
 * @Author AvA
 * @Date 2024-10-17
 */
@Service
public class NursingPlanServiceImpl implements NursingPlanService {

    @Autowired
    private NursingPlanMapper nursingPlanMapper;

    /**
     * 查询所有护理计划
     */
    @Override
    public List<NursingPlanVo> getAllNursingPlans() {
        return nursingPlanMapper.getAllNursingPlans();
    }
}
