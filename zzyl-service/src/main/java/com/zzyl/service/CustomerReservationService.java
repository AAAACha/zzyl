package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.vo.ReservationVo;

import java.time.LocalDateTime;

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
}
