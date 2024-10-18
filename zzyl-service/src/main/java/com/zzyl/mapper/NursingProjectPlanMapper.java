package com.zzyl.mapper;

import com.zzyl.entity.NursingProjectPlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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


    int insertList(List<NursingProjectPlan> projectPlanList);

    /**
     * 根据Id批量删除
     * @param ids
     */
    int deleteByIds(List<Long> ids);
}
