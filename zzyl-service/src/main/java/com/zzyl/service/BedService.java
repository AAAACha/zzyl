package com.zzyl.service;

import com.zzyl.dto.BedDto;
import com.zzyl.vo.BedVo;

import java.util.List;

/**
 * 服务接口：BedService（床位管理服务）
 */
public interface BedService {


    /**
     * 通过房间ID检索床位
     * @param roomId 房间ID
     * @return 床位视图对象列表
     */
    List<BedVo> getBedsByRoomId(Long roomId);

    /**
     * 新增床位
     * @param bedDto
     */
    void addBed(BedDto bedDto);

    /**
     * 根据id查询床位
     * @param id
     * @return
     */
    BedVo getBedById(Long id);

    /**
     * 更新床位
     * @param bedDto
     */
    void updateBed(BedDto bedDto);

    /**
     * 通过ID删除床位
     * @param id 床位ID
     */
    void deleteBedById(Long id);
}
