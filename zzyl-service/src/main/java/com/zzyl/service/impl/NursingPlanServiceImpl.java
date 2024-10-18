package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
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

    /**
     * 护理计划条件分页查询
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResponse<NursingPlanVo> page(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<NursingPlanVo> page = nursingPlanMapper.page();

        PageResponse<NursingPlanVo> pageResponse = new PageResponse(page);
        pageResponse.setRecords(page.getResult());

        return pageResponse;
    }
}
