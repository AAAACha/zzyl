package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.Reservation;
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

    /**
     * 分页查询预约
     * @param startIndex
     * @param pageSize
     * @param name
     * @param mobile
     * @param status
     * @param type
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    Page<Reservation> findByPage(@Param("page") int startIndex, @Param("pageSize") int pageSize, @Param("name") String name, @Param("mobile") String mobile, @Param("status") Integer status, @Param("type") Integer type, @Param("createBy") Long userId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
