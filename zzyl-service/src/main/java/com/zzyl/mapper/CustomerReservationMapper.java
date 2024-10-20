package com.zzyl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @Descriptioin CustomerReservationMapper
 * @Author AvA
 * @Date 2024-10-20
 */
@Mapper
public interface CustomerReservationMapper {

    /**
     * 查询取消预约数量
     * @param startTime
     * @param endTime
     * @param updateBy
     * @return
     */
    Integer getCancellationsCount(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("updateBy") Long updateBy);
}
