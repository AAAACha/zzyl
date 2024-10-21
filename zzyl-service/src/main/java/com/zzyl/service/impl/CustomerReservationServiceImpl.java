package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.ReservationDto;
import com.zzyl.entity.Reservation;
import com.zzyl.enums.BasicEnum;
import com.zzyl.enums.ReservationStatus;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.CustomerReservationMapper;
import com.zzyl.service.CustomerReservationService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.ReservationVo;
import com.zzyl.vo.TimeCountVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
     * @param updateBy
     * @return
     */
    @Override
    public Integer getCancellationsCount(Long updateBy) {
        return customerReservationMapper.getCancellationsCount(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0),
                LocalDateTime.now().withHour(23).withMinute(59).withSecond(59), updateBy);
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

    /**
     * 新增预约
     * @param reservationDto
     */
    @Override
    public void addReservation(ReservationDto reservationDto) {
        Long userId = UserThreadLocal.getUserId();

        Integer cancellationsCount = getCancellationsCount(userId);

        if(cancellationsCount >= 3){
            throw  new BaseException(BasicEnum.RESERVATION_CANCEL_COUNT_UPPER_LIMIT);
        }

        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDto,reservation);
        reservation.setStatus(ReservationStatus.PENDING.getOrdinal());
        reservation.setCreateBy(userId);

        try {
            customerReservationMapper.addReservation(reservation);
        } catch (Exception e) {
            throw new BaseException(BasicEnum.TIME_ALREADY_RESERVATED_BY_PHONE);
        }
    }

    /**
     * 查询每个时间段剩余预约次数
     * @param time
     * @return
     */
    @Override
    public List<TimeCountVo> countReservationsForEachTimeWithinTimeRange(LocalDateTime time) {
        LocalDateTime endTime = time.plusHours(24);

        return customerReservationMapper.countReservationsForEachTimeWithinTimeRange(time,endTime);
    }

    /**
     * 取消预约
     * @param id
     */
    @Override
    public void canceltReservation(Long id) {
        Reservation reservation = customerReservationMapper.findById(id);
        if(reservation != null){
            reservation.setStatus(ReservationStatus.CANCELED.getOrdinal());
            customerReservationMapper.update(reservation);
        }
    }
}
