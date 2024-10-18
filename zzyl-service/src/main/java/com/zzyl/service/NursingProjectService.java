package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.vo.NursingProjectVo;

/**
 * @Descriptioin NursingProjectService
 * @Author AvA
 * @Date 2024-10-15
 */
public interface NursingProjectService {

    /**
     * 护理项目分页查询
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<NursingProjectVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 新增护理项目
     * @param nursingProjectDto
     */
    void add(NursingProjectDto nursingProjectDto);

    /**
     * 根据id查询护理项目
     * @param id
     * @return
     */
    NursingProjectVo getById(Long id);

    /**
     * 修改护理项目信息
     * @param nursingProjectDto
     */
    void update(NursingProjectDto nursingProjectDto);

    //根据id删除护理项目
    void deleteProjectById(Long id);
}
