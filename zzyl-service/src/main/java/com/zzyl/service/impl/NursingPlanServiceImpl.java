package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.dto.NursingProjectPlanDto;
import com.zzyl.entity.NursingPlan;
import com.zzyl.mapper.NursingPlanMapper;
import com.zzyl.mapper.NursingProjectPlanMapper;
import com.zzyl.service.NursingPlanService;
import com.zzyl.service.NursingProjectPlanService;
import com.zzyl.vo.NursingPlanVo;
import com.zzyl.vo.NursingProjectPlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Descriptioin NursingPlanServiceImpl
 * @Author AvA
 * @Date 2024-10-17
 */
@Service
public class NursingPlanServiceImpl implements NursingPlanService {

    @Autowired
    private NursingPlanMapper nursingPlanMapper;

    @Autowired
    private NursingProjectPlanService nursingProjectPlanService;

    /**
     * 查询所有护理计划
     */
    @Override
    public List<NursingPlanVo> getAllNursingPlans() {
        return nursingPlanMapper.getAllNursingPlans();
    }

    /**
     * 护理计划条件分页查询
     *
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResponse<NursingPlanVo> page(String name, Integer status, Integer pageNum, Integer pageSize) {
        // 使用 PageHelper 分页插件
        PageHelper.startPage(pageNum, pageSize);
        Page<List<NursingPlan>> lists = nursingPlanMapper.page(pageNum, pageSize, name, status);// 获取所有护理计划
        return PageResponse.of(lists, NursingPlanVo.class);
    }

    /**
     * 根据id查询护理计划
     *
     * @param id
     * @return
     */
    @Override
    public NursingPlanVo getById(Long id) {
        return nursingPlanMapper.selectById(id);
    }

    /**
     * 根据id删除护理计划
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        //删除关联关系
        nursingProjectPlanService.deleteByNursingPlanId(id);
        nursingPlanMapper.deleteById(id);
    }

    /**
     * 添加护理计划
     *
     * @param nursingPlan
     */
    @Override
    public void add(NursingPlanDto nursingPlan) {
        NursingPlan plan = BeanUtil.toBean(nursingPlan, NursingPlan.class);
        plan.setStatus(1);
        nursingPlanMapper.insert(plan);
        nursingPlan.getProjectPlans().forEach(v -> v.setPlanId(plan.getId()));
        nursingProjectPlanService.addList(nursingPlan.getProjectPlans());
    }

    /**
     * 修改护理计划
     *
     * @param nursingPlan
     */
    @Override
    public void update(NursingPlanDto nursingPlan) {
        NursingPlan plan = BeanUtil.toBean(nursingPlan, NursingPlan.class);

        NursingPlanVo byId = getById(nursingPlan.getId());
        List<Long> ids = byId.getProjectPlans().stream().map(NursingProjectPlanVo::getId).collect(Collectors.toList());
        nursingProjectPlanService.deleteByIds(ids);
        if (CollUtil.isNotEmpty(nursingPlan.getProjectPlans())) {
            nursingPlan.getProjectPlans().forEach(v -> v.setPlanId(nursingPlan.getId()));
            nursingProjectPlanService.addList(nursingPlan.getProjectPlans());
        }
        nursingPlanMapper.update(plan);
    }
}
