package com.zzyl.service.impl;

import com.zzyl.mapper.CustomerReservationMapper;
import com.zzyl.service.CustomerReservationService;
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
}
