package com.zzyl.mapper;

import com.zzyl.entity.Bed;
import com.zzyl.vo.BedVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BedMapper {

    List<BedVo> getBedsByRoomId(Long roomId);

    /**
     * 新增床位
     * @param bed
     */
    void addBed(Bed bed);

    /**
     *根据ID查询床位
     * @param id
     * @return
     */
    Bed getBedById(Long id);

    /**
     * 更新床位
     * @param bed
     */
    void updateBed(Bed bed);

    /**
     * 删除床位
     * @param id
     */
    void deleteBedById(Long id);
}

