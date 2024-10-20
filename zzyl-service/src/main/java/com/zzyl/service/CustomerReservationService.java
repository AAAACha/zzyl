package com.zzyl.service;

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
}
