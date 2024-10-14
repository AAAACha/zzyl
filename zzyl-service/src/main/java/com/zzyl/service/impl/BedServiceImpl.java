package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.zzyl.dto.BedDto;
import com.zzyl.entity.Bed;
import com.zzyl.mapper.BedMapper;
import com.zzyl.service.BedService;
import com.zzyl.vo.BedVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private BedMapper bedMapper;

    @Override
    public List<BedVo> getBedsByRoomId(Long roomId) {
        return bedMapper.getBedsByRoomId(roomId);
    }

    /**
     * 新增床位
     * @param bedDto
     */
    @Override
    public void addBed(BedDto bedDto) {
        Bed bed = BeanUtil.toBean(bedDto, Bed.class);
        bed.setCreateTime(LocalDateTime.now());
        bed.setCreateBy(1L);
        bed.setBedStatus(0);
        bedMapper.addBed(bed);
    }

    /**
     * 根据id查询床位
     * @param id
     * @return
     */
    @Override
    public BedVo getBedById(Long id) {
        return BeanUtil.toBean(bedMapper.getBedById(id), BedVo.class);
    }

    /**
     * 更新床位
     * @param bedDto
     */
    @Override
    public void updateBed(BedDto bedDto) {
        Bed bed = new Bed();
        BeanUtil.copyProperties(bedDto,bed);
        bedMapper.updateBed(bed);
    }

    /**
     * 通过ID删除床位
     * @param id 床位ID
     */
    @Override
    public void deleteBedById(Long id) {
        bedMapper.deleteBedById(id);
    }
}

