package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.vo.NursingLevelVo;

import java.util.List;

public interface NursingLevelService {
    PageResponse<NursingLevelVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize);

    List<NursingLevelVo> findAll();

    void add(NursingLevelDto nursingLevelDto);

    NursingLevelVo getById(Long id);

    void update(NursingLevelDto nursingLevelDto);

    void changeStatus(int id, int status);

    void deleteById(int id);
}
