package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.zzyl.constant.CacheConstant;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.ResourceDto;
import com.zzyl.entity.Resource;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.service.ResourceService;
import com.zzyl.utils.EmptyUtil;
import com.zzyl.utils.NoProcessing;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.ResourceVo;
import com.zzyl.vo.TreeItemVo;
import com.zzyl.vo.TreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Descriptioin ResourceServiceImpl
 * @Author AvA
 * @Date 2024-10-23
 */
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    /**
     * 资源列表查询
     *
     * @param resourceDto
     * @return
     */
    @Override
    @Cacheable(value = CacheConstant.RESOURCE_LIST ,key ="#resourceDto.hashCode()")
    public List<ResourceVo> findResourceList(ResourceDto resourceDto) {
        return resourceMapper.findByCondition(resourceDto);
    }

    @Override
    @Cacheable(value = CacheConstant.RESOURCE_TREE )
    public TreeVo resourceTreeVo(ResourceDto resourceDto) {
        resourceDto.setResourceType(SuperConstant.MENU);
        resourceDto.setDataState(SuperConstant.DATA_STATE_0);
        resourceDto.setParentResourceNo(NoProcessing.processString(SuperConstant.ROOT_PARENT_ID));

        List<ResourceVo> resourceList = resourceMapper.findByCondition(resourceDto);
        if (EmptyUtil.isNullOrEmpty(resourceList)) {
            throw new RuntimeException("资源信息未定义");
        }

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setNameKey("label");
        List<Tree<String>> treeList = TreeUtil.build(resourceList, SuperConstant.ROOT_PARENT_ID,
                treeNodeConfig, (resource, treeNode) -> {
                    treeNode.setName(resource.getResourceName());
                    treeNode.setId(resource.getResourceNo());
                    treeNode.setParentId(resource.getParentResourceNo());
                });

        List<TreeItemVo> children = BeanUtil.copyToList(treeList, TreeItemVo.class);

        TreeItemVo treeItemVo = TreeItemVo.builder()
                .id(SuperConstant.ROOT_PARENT_ID)
                .label("智慧养老院")
                .children(children)
                .build();

        List<TreeItemVo> items = new ArrayList<>();
        items.add(treeItemVo);

        return TreeVo.builder()
                .items(items)
                .build();
    }

    @Override
    @Caching(evict = {@CacheEvict(value = CacheConstant.RESOURCE_LIST ,allEntries = true),
            @CacheEvict(value = CacheConstant.RESOURCE_TREE ,allEntries = true)})
    public Boolean createResource(ResourceDto resourceDto) {
        Resource resource = BeanUtil.toBean(resourceDto, Resource.class);
        Resource parentResourceNo =  resourceMapper.selectByParentResourceNo(resource.getParentResourceNo());
        resource.setDataState(parentResourceNo.getDataState());

        String resourceNo = createResourceNo(resource);
        resource.setResourceNo(resourceNo);

        resourceMapper.insert(resource);

        return  true;
    }


    private String createResourceNo(Resource resource) {
        if(resource.getResourceType().equals("m") &&
                NoProcessing.processString(resource.getParentResourceNo()).length() / 3 > 4){
            throw new BaseException(BasicEnum.RESOURCE_DEPTH_UPPER_LIMIT);
        }

        ResourceDto dto = ResourceDto.builder().parentResourceNo(resource.getParentResourceNo()).build();
        List<ResourceVo> resourceVoList = resourceMapper.findByCondition(dto);

        if(ObjectUtil.isNotEmpty(resourceVoList)){
            List<String> resourceNoList = resourceVoList.stream().map(ResourceVo::getResourceNo).collect(Collectors.toList());

            Long maxResourceNo = resourceNoList.stream().map(i -> Long.valueOf(i)).max(Comparator.comparing(i -> i)).get();

            return NoProcessing.createNo(maxResourceNo.toString(),true);
        } else {
            return NoProcessing.createNo(resource.getParentResourceNo(),false);
        }
    }

    @Override
    @Caching(evict = {@CacheEvict(value = CacheConstant.RESOURCE_LIST ,allEntries = true),
            @CacheEvict(value = CacheConstant.RESOURCE_TREE ,allEntries = true)})
    public void enableOrDisable(ResourceDto resourceDto) {
        Resource resource = BeanUtil.toBean(resourceDto, Resource.class);
        if(resource == null){
            throw new RuntimeException();
        }

        if("0".equals(resource.getDataState())){
            String parentResourceNo = resource.getParentResourceNo();
            Resource parentResource = resourceMapper.getResourceByResourceNo(parentResourceNo);
            if(parentResource != null && "1".equals(parentResource.getDataState()))
                throw new BaseException(BasicEnum.PARENT_MENU_DISABLE);
        }

        String resourceNo = resource.getResourceNo();
        resourceMapper.updateByResourceNo(resourceNo, resource.getDataState());

        resourceMapper.updateByParentResourceNo(NoProcessing.processString(resourceNo), //更新前对子资源进行处理自动补全
                resource.getDataState());
    }

    @Override
    @Caching(evict = {@CacheEvict(value = CacheConstant.RESOURCE_LIST ,allEntries = true),
            @CacheEvict(value = CacheConstant.RESOURCE_TREE ,allEntries = true)})
    public void updateResource(ResourceDto resourceDto) {
        Resource resource = BeanUtil.toBean(resourceDto, Resource.class);
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = CacheConstant.RESOURCE_LIST ,allEntries = true),
            @CacheEvict(value = CacheConstant.RESOURCE_TREE ,allEntries = true)})
    public void deleteByResourceNo(String resourceNo) {
        if (hasChildByMenuId(resourceNo)) {
            throw new BaseException(BasicEnum.CONTAINS_CHILD_RESOURCE_DELETE_FAILED);
        }

        Resource resource = resourceMapper.getResourceByResourceNo(resourceNo);

        if (resource == null || resource.getDataState().equals(SuperConstant.DATA_STATE_0)) {
            throw new BaseException(BasicEnum.RESOURCE_ENABLE_DELETE_FAILED);
        }

        resourceMapper.deleteByResourceNo(resourceNo);
    }

    /**
     * 查询是否有子菜单
     *
     * @param resourceNo 资源编号
     * @return
     */
    public boolean hasChildByMenuId(String resourceNo) {
        //1、调用Mapper进行查询是否有子菜单
        int result = resourceMapper.hasChildByMenuId(resourceNo);

        //2、将查询结果返回
        return result > 0 ? true : false;

    }
}
