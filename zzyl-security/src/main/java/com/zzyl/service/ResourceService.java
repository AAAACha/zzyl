package com.zzyl.service;

import com.zzyl.dto.ResourceDto;
import com.zzyl.vo.ResourceVo;

import java.util.List;

/**
 * @Descriptioin ResourceService
 * @Author AvA
 * @Date 2024-10-23
 */
public interface ResourceService {

    /**
     * 资源列表查询
     * @param resourceDto
     * @return
     */
    List<ResourceVo> findResourceList(ResourceDto resourceDto);
}
