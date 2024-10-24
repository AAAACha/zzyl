package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.dto.RoleDto;
import com.zzyl.entity.Role;
import com.zzyl.vo.RoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table sys_role
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<Role> list);

    /**
     * 角色分页查询
     * @param roleDto
     * @return
     */
    Page<RoleVo> findRoleVoPage(RoleDto roleDto);

    @Select("select * from sys_role where data_state = '0' order by create_time desc")
    List<Role> initRoles();

    List<RoleVo> findRoleVoListInUserId(@Param("userIds")List<Long> userIds);
}