package com.zzyl.service;

import com.zzyl.dto.ResourceDto;
import com.zzyl.vo.MenuVo;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeVo;

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

    TreeVo resourceTreeVo(ResourceDto resourceDto);

    Boolean createResource(ResourceDto resourceDto);

    void enableOrDisable(ResourceDto resourceDto);

    void updateResource(ResourceDto resourceDto);

    void deleteByResourceNo(String resourceNo);

    /**
     * 根据用户id查询对应的资源数据
     * @param userId
     * @return
     */
    List<MenuVo> menus(Long userId);
}
