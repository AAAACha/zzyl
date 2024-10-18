package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingProject;
import com.zzyl.vo.NursingProjectVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Descriptioin NursingProjectMapper
 * @Author AvA
 * @Date 2024-10-15
 */
@Mapper
public interface NursingProjectMapper {

    /**
     *护理项目分页查询
     * @param name
     * @param status
     * @return
     */
    Page<NursingProjectVo> selectByPage(@Param("name") String name, @Param("status") Integer status);

    /**
     * 新增护理项目
     * @param nursingProject
     */
    void insert(NursingProject nursingProject);

    /**
     * 根据id查询护理项目
     * nursingProject
     * @param id
     * @return
     */
    NursingProject selectById(Long id);

    void update(NursingProject nursingProject);

    /**
     * 根据id删除护理项目
     * @param id
     */
    void deleteProjectById(Long id);

    @Update("UPDATE nursing_project np set status=#{status} WHERE id=#{id}")
    void updateStatus(int id, int status);
}
