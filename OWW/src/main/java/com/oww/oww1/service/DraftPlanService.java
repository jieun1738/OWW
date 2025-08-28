package com.oww.oww1.service;

import java.util.List;
import java.util.Map;

import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;

import jakarta.servlet.http.HttpSession;

/**
 * 임시 DIY 플랜을 세션/메모리 수준에서 관리하는 서비스.
 * - 목록/검색/상품 정보는 DB(Mapper)에서 가져오고
 * - 선택 상태만 메모리에 보관
 */
public interface DraftPlanService {

    /** 유저의 활성 임시 플랜을 반환(없으면 생성) */
    PlanVO getOrCreate(String userEmail);

    /** 활성 임시 플랜의 선택된 상품 목록(카테고리별 0~3) */
    List<ProductVO> getSelectedProducts(String userEmail);

    /** 활성 임시 플랜의 총 비용 */
    int getTotalCost(String userEmail);

    /** 카테고리(0:홀,1:스튜디오,2:드레스,3:메이크업)에 상품 선택 */
    void select(String userEmail, int productNo, int category);

    /** 해당 카테고리 선택 해제 */
    void deleteOne(String userEmail, int category);

    /** 활성 임시 플랜의 모든 선택 초기화 */
    void reset(String userEmail);

    /** 새 임시 플랜 슬롯 추가(활성 플랜이 새로 만들어짐) */
    void addNewPlan(String userEmail);

    // ===== DIY 현재 선택 조작 (세션 currentPlan 사용) =====
    void setCurrentItem(HttpSession session, int category, int productNo); // 0:hall,1:studio,2:dress,3:makeup
    Map<String, Object> getCurrentPlan(HttpSession session);
    void resetCurrentPlan(HttpSession session);

    // ===== 임시보관함(planBox) 저장 =====
    // (이전 컨트롤러 호환: userEmail만 받는 구형 시그니처도 유지)
    void saveCurrentToBox(String userEmail);
    void saveCurrentToBox(HttpSession session, String userEmail);

    // Confirm 페이지에서 '결정' → DB저장 없이 보관함으로만
    void saveConfirmContextToBox(HttpSession session, String userEmail);

    // ===== 임시보관함 관리 =====
    List<Map<String, Object>> getPlanBox(HttpSession session);
    void removeFromBox(HttpSession session, int index);
    void clearPlanBox(HttpSession session);

    // ===== 최종 확정(DB 저장 + 보관함 제거) =====
    void finalizeFromBox(String userEmail, int index, HttpSession session);
}
