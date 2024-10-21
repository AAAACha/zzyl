package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.Reservation;
import com.zzyl.vo.TimeCountVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Descriptioin CustomerReservationMapper
 * @Author AvA
 * @Date 2024-10-20
 */
@Mapper
public interface CustomerReservationMapper {

    /**
     * 新增预约
     */
    void addReservation(Reservation reservation);

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

    /**
     * 查询每个时间段剩余预约次数
     * @param startTime
     * @param endTime
     * @return
     */
    List<TimeCountVo> countReservationsForEachTimeWithinTimeRange(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**根据Id查找预约信息
     *
     * @param id
     * @return
     */
    Reservation findById(Long id);

    /**
     * 更新预约信息
     * @param reservation
     */
    void update(Reservation reservation);

    @Update("update reservation set status = 3 where status = 0 and time <= #{minusDays}")
    void updateReservationStatus(LocalDateTime minusDays);
}
