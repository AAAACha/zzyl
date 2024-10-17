package com.zzyl.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.BedDto;
import com.zzyl.service.BedService;
import com.zzyl.vo.BedVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bed")
@Api(tags = "床位管理相关接口")
public class BedController extends BaseController {

    @Autowired
    private BedService bedService;

    @GetMapping("/read/room/{roomId}")
    @ApiOperation(value = "根据房间id查询床位", notes = "传入房间id")
    public ResponseResult<List<BedVo>> readBedByRoomId(
            @ApiParam(value = "房间ID", required = true) @PathVariable("roomId") Long roomId) {
        List<BedVo> beds = bedService.getBedsByRoomId(roomId);
        return success(beds);
    }

    /**
     * 新增床位
     * @param bedDto
     * @return
     */
    @PostMapping("/create")
    @ApiOperation("添加床位")
    @ApiOperationSupport(includeParameters = {"bedDto.bedNumber","bedDto.sort","bedDto.roomId"})
    @ApiImplicitParam(name = "bedDto", value = "床位对象", required = true, dataType = "BedDto")
    //参数描述方式1  方法上@ApiImplicitParam
    //   name = "bedDto" 设置参数名，表示对那个参数描述,注意：这个名字不可以任意写，必须与方法的参数名一致
    //   value = "床位对象Dto" 设置参数中文别名
    //   required = true 参数不能为空，必须传值
    //   dataType = "BedDto" 设置参数所属类型
    public ResponseResult addBed(@RequestBody BedDto bedDto){
        logger.info("添加床位:{}",bedDto);
        bedService.addBed(bedDto);
        return success();
    }

    /**
     * 根据ID查询床位
     * @param id
     * @return
     */
    @GetMapping("/read/{id}")
    @ApiOperation("根据ID查询床位")
    public ResponseResult readBed(@PathVariable("id") Long id){
        logger.info("根据Id查询床位:{}",id);
        BedVo bed = bedService.getBedById(id);
        return success(bed);
    }

    /**
     * 更新床位
     * @param bedDto
     * @return
     */
    @PutMapping("/update")
    @ApiOperation("更新床位")
    public ResponseResult updateBed(@RequestBody BedDto bedDto){
        logger.info("更新床位:{}",bedDto);
        bedService.updateBed(bedDto);
        return success();
    }

    /**
     * 删除床位
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除床位")
    public ResponseResult deleteBed(@PathVariable("id") Long id) {
        logger.info("根据Id删除床位:{}",id);
        bedService.deleteBedById(id);
        return success();
    }
}
