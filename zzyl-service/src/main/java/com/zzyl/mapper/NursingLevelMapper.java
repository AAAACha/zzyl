package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.vo.NursingLevelVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NursingLevelMapper {
    Page<NursingLevelVo> findByCondition(@Param("name") String name, @Param("status") Integer status);

    List<NursingLevelVo> findAll();

    void insert(NursingLevelVo nursingLevelVo);


    NursingLevelVo selectById(Long id);

    void update(NursingLevelVo nursingLevelVo);

    @Update("update  nursing_level set status=#{status} where id=#{id}")
    void updataStatus(int id, int status);

    @Delete("DELETE FROM nursing_level WHERE id = #{id}")
    void deleteById(int id);
}
