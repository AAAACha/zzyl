package com.zzyl.mapper;

import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.vo.ResourceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table sys_resource
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<Resource> list);

    /**
     * 资源列表查询
     * @param resourceDto
     * @return
     */
    List<ResourceVo> findByCondition(ResourceDto resourceDto);
}