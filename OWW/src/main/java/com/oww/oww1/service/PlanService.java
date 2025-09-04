package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;

public interface PlanService {
    List<PlanVO> listFinalPlans(String userEmail);
    List<ProductVO> listProductsOfPlan(int planNo);
    int finalizeDIY(PlanVO vo);

    void finalizeFromTempBox(String userEmail, int index);
}
