package com.zzyl.mapper;

import com.zzyl.vo.NursingPlanVo;
import org.apache.ibatis.annotations.Mapper;

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
}
