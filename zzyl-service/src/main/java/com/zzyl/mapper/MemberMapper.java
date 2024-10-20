package com.zzyl.mapper;

import com.zzyl.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Descriptioin MemberMapper
 * @Author AvA
 * @Date 2024-10-20
 */
@Mapper
public interface MemberMapper {

    @Select("select * from member where open_id = #{openId}")
    Member getByOpenId(String openId);

    void update(Member member);

    void save(Member member);
}
