package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.dto.UserDto;
import com.zzyl.entity.User;
import com.zzyl.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 这是Mybatis Generator拓展插件生成的方法(请勿删除).
     * This method corresponds to the database table sys_user
     *
     * @mbg.generated
     * @author hewei
     */
    int batchInsert(@Param("list") List<User> list);

    Page<User> userPage(UserDto userDto);

    @Update("update sys_user set data_state = #{status} where id = #{id}")
    void enableOrDisable(@Param("id") Long id, @Param("status") String status);

    @Update("update sys_user set is_delete = 1 where id = #{userId}")
    void deleteUserById(@Param("userId") Long userId);

    List<User> selectList(UserDto userDto);
}