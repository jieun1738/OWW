package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oww.oww1.VO.PlanVO;

/** PLAN 확정 저장/조회 매퍼 */
@Mapper
public interface PlanMapper {
    void insertPlan(PlanVO vo);

    List<PlanVO> findByUser(@Param("userEmail") String userEmail);
}
