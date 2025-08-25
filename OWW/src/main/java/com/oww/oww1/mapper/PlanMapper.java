package com.oww.oww1.mapper;

import java.util.List;

import com.oww.oww1.VO.PlanVO;

public interface PlanMapper {
    List<PlanVO> selectPlansByUser(String userEmail);
    int insertPlan(PlanVO vo); // plan_no는 트리거로 자동
}
