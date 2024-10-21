package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.entity.Reservation;
import com.zzyl.mapper.CustomerReservationMapper;
import com.zzyl.service.CustomerReservationService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.ReservationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Descriptioin CustomerReservationServiceImpl
 * @Author AvA
 * @Date 2024-10-20
 */
@Service
public class CustomerReservationServiceImpl implements CustomerReservationService {

    @Autowired
    private CustomerReservationMapper customerReservationMapper;

    /**
     * 查询取消预约数量
     * @param userId
     * @return
     */
    @Override
    public Integer getCancellationsCount(Long userId) {
        return customerReservationMapper.getCancellationsCount(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0),
                LocalDateTime.now().withHour(23).withMinute(59).withSecond(59), userId);
    }

    /**
     * 分页查询预约
     * @param page
     * @param size
     * @param name
     * @param phone
     * @param status
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public PageResponse<ReservationVo> findByPage(int page, int size, String name, String phone, Integer status, Integer type, LocalDateTime startTime, LocalDateTime endTime) {
        PageHelper.startPage(page, size);
        Long userId = UserThreadLocal.getUserId();
        Page<Reservation> byPage = customerReservationMapper.findByPage(page, size, name, phone, status, type, userId, startTime, endTime);
        return PageResponse.of(byPage, ReservationVo.class);
    }
}
