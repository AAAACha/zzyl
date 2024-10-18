package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingPlan;
import com.zzyl.vo.NursingPlanVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Descriptioin NursingPlanMapper
 * @Author AvA
 * @Date 2024-10-17
 */
@Mapper
public interface NursingPlanMapper {

    /**
     * 查询所有护理计划
     * @return
     */
    List<NursingPlanVo> getAllNursingPlans();

    /**
     * 护理计划条件分页查询
     * @return
     */
    Page<NursingPlanVo> page(String name, Integer status);

    /**
     * 根据id查询护理计划
     * @param id
     * @return
     */
    NursingPlan selectById(Long id);
}
