package com.zzyl.job;

import com.zzyl.service.CustomerReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * @Descriptioin ReservationJob
 * @Author AvA
 * @Date 2024-10-21
 */
@Component
@Slf4j
public class ReservationJob {

    @Autowired
    private CustomerReservationService customerReservationService;

    @Scheduled(cron = "0 1,31 * * * ?")
    public void updateReservationStatus(){
        log.info("预约状态-过期修改-begin");
        customerReservationService.updateReservationStatus(LocalDateTime.now());
        log.info("预约状态-过期修改-end");
    }
}
