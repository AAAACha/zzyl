package com.zzyl.controller.customer;

import com.zzyl.base.ResponseResult;
import com.zzyl.service.CustomerReservationService;
import com.zzyl.utils.UserThreadLocal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Descriptioin CustomerReservationController
 * @Author AvA
 * @Date 2024-10-20
 */
@Slf4j
@Api(tags = "参观预约接口")
@RestController
@RequestMapping("/customer/reservation")
public class CustomerReservationController {

    @Autowired
    private CustomerReservationService customerReservationService;

    /**
     * 查询取消预约数量
     * @return
     */
    @GetMapping
    @ApiOperation("查询取消预约数量")
    public ResponseResult<Integer> getCancellationsCount(){

        Long userId = UserThreadLocal.getUserId();
        Integer count =  customerReservationService.getCancellationsCount(userId);

        return ResponseResult.success();
    }
}
