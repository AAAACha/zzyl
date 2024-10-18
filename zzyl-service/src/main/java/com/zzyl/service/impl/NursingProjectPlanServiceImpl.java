package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zzyl.dto.NursingProjectPlanDto;
import com.zzyl.entity.NursingProjectPlan;
import com.zzyl.mapper.NursingProjectPlanMapper;
import com.zzyl.service.NursingProjectPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.data.rest.BasePathAwareServicesProvider;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public int addList(List<NursingProjectPlanDto> projectPlans) {
        List<NursingProjectPlan> projectPlanList = projectPlans.stream().map(v -> BeanUtil.toBean(v, NursingProjectPlan.class)).collect(Collectors.toList());
        return nursingProjectPlanMapper.insertList(projectPlanList);
    }
}
