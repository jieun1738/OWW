package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;

public interface PlanService {

    // 최종결정된 플랜 목록 (사용자 기준)
    List<PlanVO> listFinalPlans(String userEmail);

    // 특정 플랜에 속한 상품 목록
    List<ProductVO> listProductsOfPlan(int planNo);

    // DIY 최종결정 (PlanVO의 userEmail/hall/studio/dress/makeup 사용)
    int finalizeDIY(PlanVO vo);

    // 임시 플랜 보관함(index)에서 최종결정
    void finalizeFromTempBox(String userEmail, int index);

    // 사용자 최종 플랜 전부 삭제 (항상 1건만 유지하기 위한 선삭제용)
    void deleteFinalByUser(String userEmail);

    // (옵션) planNo로 삭제가 필요할 때 사용
    void deleteFinalByPlanNo(int planNo);
}
