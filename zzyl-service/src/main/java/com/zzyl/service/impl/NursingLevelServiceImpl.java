package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.mapper.NursingLevelMapper;
import com.zzyl.service.NursingLevelService;
import com.zzyl.vo.NursingLevelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * grouping_zzyl
 * 2024/10/17 21:18
 * NursingLevelServiceImpl By 1mZ
 */
@Service
public class NursingLevelServiceImpl implements NursingLevelService {
    @Autowired
    private NursingLevelMapper nursingLevelMapper;
    @Override
    public PageResponse<NursingLevelVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<NursingLevelVo> page=nursingLevelMapper.findByCondition(name,status);
        PageResponse<NursingLevelVo> pageResponse = new PageResponse<>(page);//里面会自动封装页码，每页大小，总条数，总页数
        pageResponse.setRecords(page.getResult());//封装当前页数据列表
        //4.返回数据
        return pageResponse;
    }

    @Override
    public List<NursingLevelVo> findAll() {
        return nursingLevelMapper.findAll();
    }

    @Override
    public void add(NursingLevelDto nursingLevelDto) {
        NursingLevelVo nursingLevelVo = BeanUtil.toBean(nursingLevelDto, NursingLevelVo.class);
        nursingLevelMapper.insert(nursingLevelVo);
    }

    @Override
    public NursingLevelVo getById(Long id) {
        return nursingLevelMapper.selectById(id);
    }

    @Override
    public void update(NursingLevelDto nursingLevelDto) {
        NursingLevelVo nursingLevelVo = BeanUtil.toBean(nursingLevelDto, NursingLevelVo.class);
        nursingLevelMapper.update(nursingLevelVo);
    }

    @Override
    public void changeStatus(int id, int status) {
        nursingLevelMapper.updataStatus(id,status);
    }

    @Override
    public void deleteById(int id) {
        nursingLevelMapper.deleteById(id);
    }
}
