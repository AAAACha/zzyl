package com.zzyl.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Descriptioin NursingProjectPlanMapper
 * @Author AvA
 * @Date 2024-10-18
 */
@Mapper
public interface NursingProjectPlanMapper {
    /**
     * 根据护理计划删除关联关系
     * @param id
     */
    @Delete("delete from nursing_project_plan where plan_id = #{nursingPlanId}")
    void deleteByNursingPlanId(Long id);
}
