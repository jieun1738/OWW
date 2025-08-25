package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.PlanVO;

public interface PlanService {
    List<PlanVO> findPlansByUser(String userEmail);
    int savePlan(PlanVO vo);
}
