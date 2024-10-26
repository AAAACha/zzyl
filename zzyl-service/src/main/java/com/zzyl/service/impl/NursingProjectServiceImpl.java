package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingProjectDto;
import com.zzyl.entity.NursingProject;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.service.NursingProjectService;
import com.zzyl.vo.NursingProjectVo;
import org.apache.ibatis.cache.CacheKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Descriptioin NursingProjectServiceImpl
 * @Author AvA
 * @Date 2024-10-15
 */
@Service
public class NursingProjectServiceImpl implements NursingProjectService {

    private static final String CACHE_KEY_PREFIX = "nursing_project::*";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private NursingProjectMapper nursingProjectMapper;

    /**
     * 护理项目分页查询
     *
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResponse<NursingProjectVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize) {

        String key = "nursing_project::" + (name == null ? "" : name + "_") + (status == null ? "" : status + "_") + pageNum + "_" + pageSize;
        String json = redisTemplate.opsForValue().get(key);

        PageResponse<NursingProjectVo> pageResponse = null;

        if (StrUtil.isEmpty(json)) {
            //定义PageHelper的页码和每页大小
            PageHelper.startPage(pageNum, pageSize);

            //执行分页查询数据,返回 Page<NursingProjectVo>
            Page<NursingProjectVo> page = nursingProjectMapper.selectByPage(name, status);

            //将查询返回结果封装为 PageResponse<NursingProjectVo>
            pageResponse = new PageResponse(page);
            pageResponse.setRecords(page.getResult());//封装当前页数的数据列表
            redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(pageResponse), 30, TimeUnit.MINUTES);
        } else {
            pageResponse = JSONUtil.toBean(json, PageResponse.class);
        }

        //返回数据
        return pageResponse;
    }

    /**
     * 新增护理项目
     *
     * @param nursingProjectDto
     */
    @Override
    public void add(NursingProjectDto nursingProjectDto) {
        NursingProject nursingProject = new NursingProject();
        BeanUtils.copyProperties(nursingProjectDto, nursingProject);
        nursingProjectMapper.insert(nursingProject);
        clearCache(CACHE_KEY_PREFIX);
    }

    /**
     * 根据id查询护理项目
     *
     * @param id
     * @return
     */
    @Override
    public NursingProjectVo getById(Long id) {
        NursingProject nursingProject = nursingProjectMapper.selectById(id);
        return BeanUtil.toBean(nursingProject, NursingProjectVo.class);
    }

    /**
     * 修改护理项目信息
     *
     * @param nursingProjectDto
     */
    @Override
    public void update(NursingProjectDto nursingProjectDto) {
        NursingProject nursingProject = new NursingProject();
        BeanUtils.copyProperties(nursingProjectDto, nursingProject);
        nursingProjectMapper.update(nursingProject);
        clearCache(CACHE_KEY_PREFIX);
    }

    /**
     * 根据id删除护理项目
     */
    @Override
    public void deleteProjectById(Long id) {
        nursingProjectMapper.deleteProjectById(id);
    }

    @Override
    public void updateStatus(int id, int status) {
        nursingProjectMapper.updateStatus(id, status);
        clearCache(CACHE_KEY_PREFIX);
    }

    /**
     * 查询所有护理项目
     *
     * @return
     */
    @Override
    public List<NursingProjectVo> selectAll() {
        return nursingProjectMapper.selectAll();
    }

    /**
     * 清除缓存数据
     * @param key
     */
    private void clearCache(String key){
        Set<String> keys = redisTemplate.keys(key);
        redisTemplate.delete(keys);
    }
}
