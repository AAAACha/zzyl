package com.zzyl.controller.customer;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Descriptioin CustomOrderController
 * @Author AvA
 * @Date 2024-10-20
 */
@Slf4j
@Api(tags = "客户管理")
@RestController
@RequestMapping("/customer/orders")
public class CustomOrderController {

    @Autowired
    private NursingProjectService nursingProjectService;

    @GetMapping("/project/page")
    @ApiOperation("分页查询护理项目列表")
    public ResponseResult<PageResponse<NursingProjectVo>> list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        PageResponse<NursingProjectVo> page = nursingProjectService.getByPage(name, status, pageNum, pageSize);

        return ResponseResult.success(page);
    }

    @GetMapping("/project/{id}")
    @ApiOperation("根据编号查询护理项目信息")
    public ResponseResult<NursingProjectVo> getById(@PathVariable long id){
        NursingProjectVo nursingProject = nursingProjectService.getById(id);

        return ResponseResult.success(nursingProject);
    }
}
