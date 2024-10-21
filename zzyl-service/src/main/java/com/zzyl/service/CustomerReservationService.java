package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.ReservationDto;
import com.zzyl.vo.ReservationVo;
import com.zzyl.vo.TimeCountVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Descriptioin CustomerReservationService
 * @Author AvA
 * @Date 2024-10-20
 */
public interface CustomerReservationService {

    /**
     * 查询取消预约数量
     * @param userId
     * @return
     */
    Integer getCancellationsCount(Long userId);

    /**
     * 分页查询预约
     * @param pageNum
     * @param pageSize
     * @param name
     * @param phone
     * @param status
     * @param type
     * @param localDateTime
     * @param localDateTime1
     * @return
     */
    PageResponse<ReservationVo> findByPage(int pageNum, int pageSize, String name, String phone, Integer status, Integer type, LocalDateTime localDateTime, LocalDateTime localDateTime1);

    /**
     * 新增预约
     */
    void addReservation(ReservationDto reservationDto);

    /**
     * 查询每个时间段剩余预约次数
     * @param time
     * @return
     */
    List<TimeCountVo> countReservationsForEachTimeWithinTimeRange(LocalDateTime time);

    /**
     * 取消预约
     * @param id
     */
    void canceltReservation(Long id);

    void updateReservationStatus(LocalDateTime now);
}
