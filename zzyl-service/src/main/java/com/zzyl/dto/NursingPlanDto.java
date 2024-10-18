package com.zzyl.dto;

import com.zzyl.base.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Descriptioin NursingPlanDto
 * @Author AvA
 * @Date 2024-10-17
 */
@Data
public class NursingPlanDto extends BaseDto {
    /**
     * 编号
     */
    @ApiModelProperty(value = "编号")
    private Long id;

    /**
     * 排序号
     */
    @ApiModelProperty(value = "排序号")
    private Integer sortNo;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String planName;

    /**
     * 状态 (0:禁用, 1:启用)
     */
    @ApiModelProperty(value = "状态 (0:禁用, 1:启用)")
    private Integer status;
}
