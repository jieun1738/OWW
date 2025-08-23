package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;

/**
 * 가장 기본적인 서비스 계층
 * - 자료형 단순화(int, String)
 * - 흐름: Controller → Service → Mapper(XML) → DB
 */
public interface PlannerService {
    // [DIY] 카테고리별 상품 목록
    List<ProductVO> findProductsByCategory(int category);

    // [패키지] 목록/상세
    List<PackageVO> listPackages();
    PackageVO getPackage(int packageNo);

    // [확정] plan 저장 및 사용자별 목록
    void confirmPlan(String userEmail, Integer packageNo, int hall, int studio, int dress, int makeup);
    List<PlanVO> getPlansByUser(String userEmail);
}
