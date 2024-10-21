package com.zzyl.controller.customer;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.ReservationDto;
import com.zzyl.service.CustomerReservationService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.ReservationVo;
import com.zzyl.vo.TimeCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.zzyl.base.ResponseResult.success;

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
    @GetMapping("/cancelled-count")
    @ApiOperation("查询取消预约数量")
    public ResponseResult<Integer> getCancellationsCount(){

        Long userId = UserThreadLocal.getUserId();
        Integer count =  customerReservationService.getCancellationsCount(userId);

        return success(count);
    }

    /**
     * 分页查询预约
     * @param pageNum
     * @param pageSize
     * @param name
     * @param phone
     * @param status
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询预约")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "预约人姓名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "状态", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "long", paramType = "query")
    })
    public ResponseResult<PageResponse<ReservationVo>> findByPage(@RequestParam(defaultValue = "1") int pageNum,
                                                                  @RequestParam(defaultValue = "10") int pageSize,
                                                                  @RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) String phone,
                                                                  @RequestParam(required = false) Integer status,
                                                                  @RequestParam(required = false) Integer type,
                                                                  @RequestParam(required = false) Long startTime,
                                                                  @RequestParam(required = false) Long endTime) {
        PageResponse<ReservationVo> byPage = customerReservationService.findByPage(pageNum, pageSize, name, phone, status, type, ObjectUtil.isEmpty(startTime)? null : LocalDateTimeUtil.of(startTime), ObjectUtil.isEmpty(endTime)? null : LocalDateTimeUtil.of(endTime));
        return success(byPage);
    }

    /**
     * 新增预约
     * @param reservationDto
     * @return
     */
    @PostMapping()
    @ApiOperation("新增预约")
    public ResponseResult addReservation(@RequestBody ReservationDto reservationDto){
        customerReservationService.addReservation(reservationDto);
        return success();
    }

    /**
     * 查询每个时间段剩余预约次数
     * @param time
     * @return
     */
    @GetMapping("/countByTime")
    @ApiOperation("查询每个时间段剩余预约次数")
    public ResponseResult countReservationsForEachTimeWithinTimeRange (@RequestParam Long time){
        List<TimeCountVo> list = customerReservationService.countReservationsForEachTimeWithinTimeRange(LocalDateTimeUtil.of(time));

        return success(list);
    }

    /**
     * 取消预约
     * @param id
     * @return
     */
    @PutMapping("/{id}/cancel")
    @ApiOperation("取消预约")
    public ResponseResult canceltReservation(@PathVariable Long id){
        customerReservationService.canceltReservation(id);

        return success();
    }
}
