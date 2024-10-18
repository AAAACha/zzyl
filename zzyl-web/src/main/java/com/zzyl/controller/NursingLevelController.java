package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.service.NursingLevelService;
import com.zzyl.vo.NursingLevelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * grouping_zzyl
 * 2024/10/17 20:58
 * NursingLevelController By 1mZ
 */
@RestController
@RequestMapping("/nursingLevel")
@Api(tags ="护理等级相关接口")
public class NursingLevelController extends BaseController {
    @Autowired
    private NursingLevelService nursingLevelService;

    @GetMapping("/listByPage")
    @ApiOperation(value = "分页查询", notes = "传入房间护理等级属性")
    public ResponseResult<PageResponse<NursingLevelVo>> getByPage(
            @ApiParam(value = "护理等级名称") String name,
            @ApiParam(value = "状态：0-禁用，1-启用") Integer status,
            @ApiParam(value = "当前页码")
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页显示数量")
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        logger.info("分页查询护理等级列表,请求参数：name={}，status={}，pageNum={}，pageSize={}",
                name, status, pageNum, pageSize);
        PageResponse<NursingLevelVo> pageResponse = nursingLevelService.getByPage(name, status, pageNum, pageSize);
        return success(pageResponse);
    }

    @GetMapping("/listAll")
    @ApiOperation("查询所有护理等级")
    public ResponseResult<List<NursingLevelVo>> findAll() {
        List<NursingLevelVo> nursingLevelVo = nursingLevelService.findAll();
        return success(nursingLevelVo);
    }

    @PostMapping("/insert")
    @ApiOperation("新增护理等级")
    public ResponseResult add(@RequestBody NursingLevelDto nursingLevelDto) {
        nursingLevelService.add(nursingLevelDto);
        return success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询护理项目")
    public ResponseResult<NursingLevelVo> getById(
            @PathVariable Long id) {
        logger.info("根据id查询护理项目,请求参数：{}", id);

        //调用业务获取护理项目详情 NursingProjectVo
        NursingLevelVo nursingLevelVo = nursingLevelService.getById(id);

        //返回数据
        return success(nursingLevelVo);
    }

    @PutMapping("/update")
    @ApiOperation("修改护理项目")
    public ResponseResult update(@RequestBody NursingLevelDto nursingLevelDto){
        logger.info("修改护理项目，请求参数：{}",nursingLevelDto);

        //调用业务修改护理项目
        nursingLevelService.update(nursingLevelDto);

        //返回数据
        return success();
    }
    @PutMapping("/{id}/status/{status}")
    @ApiOperation("启用与禁用")
    public ResponseResult changeStatus(@PathVariable int id,
                                 @PathVariable int status){
        nursingLevelService.changeStatus(id,status);
        return success();
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation("删除")
    public ResponseResult deleteById(@PathVariable int id){
        nursingLevelService.deleteById(id);
        return success();
    }
}
