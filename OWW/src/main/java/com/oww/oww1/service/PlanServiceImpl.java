package com.oww.oww1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.mapper.PlanMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {
    private final PlanMapper planMapper;

    @Override
    public List<PlanVO> findPlansByUser(String userEmail) {
        return planMapper.selectPlansByUser(userEmail);
    }

    @Override
    public int savePlan(PlanVO vo) {
        return planMapper.insertPlan(vo);
    }
}
