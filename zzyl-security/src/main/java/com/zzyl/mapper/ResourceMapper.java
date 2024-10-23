package com.zzyl.mapper;

import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.vo.ResourceVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    @Select("select * from sys_resource where resource_no = #{parentResourceNo}")
    Resource selectByParentResourceNo(String parentResourceNo);


    /**
     * 根据资源编号查询资源
     * @param ResourceNo
     * @return
     */
    Resource getResourceByResourceNo(@Param("ResourceNo") String ResourceNo);

    /**
     * 修改子资源编号状态：根据子资源编号和前端传过来的状态进行修改状态
     * @param resourceNo 子资源编号
     * @param dataState  资源状态
     */
    void updateByResourceNo(@Param("resourceNo") String resourceNo, @Param("dataState") String dataState);

    /**
     * 修改父资源编号状态：根据子资源编号和前端传过来的资源状态进行修改
     * @param resourceNo    子资源编号
     * @param dataState     资源状态
     */
    void updateByParentResourceNo(@Param("resourceNo") String resourceNo, @Param("dataState") String dataState);

    void deleteByResourceNo(String resourceNo);

    int hasChildByMenuId(@Param("resourceNo")String resourceNo);
}