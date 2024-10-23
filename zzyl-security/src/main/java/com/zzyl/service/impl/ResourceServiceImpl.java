package com.zzyl.service.impl;

import com.zzyl.dto.ResourceDto;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.service.ResourceService;
import com.zzyl.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descriptioin ResourceServiceImpl
 * @Author AvA
 * @Date 2024-10-23
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 资源列表查询
     * @param resourceDto
     * @return
     */
    @Override
    public List<ResourceVo> findResourceList(ResourceDto resourceDto) {
        return resourceMapper.findByCondition(resourceDto);
    }
}
