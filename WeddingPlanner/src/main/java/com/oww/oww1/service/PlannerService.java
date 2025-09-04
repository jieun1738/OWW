package com.wedding.oww.service;

import com.wedding.oww.vo.*;

import java.util.List;

public interface PlannerService {

    /* 제품/패키지 조회 */
    List<ProductVO> getProducts(Integer category, String q, String sort);
    List<ProductVO> getProductsByIds(List<Long> ids);
    List<PackageVO> getPackages(Integer type);
    PackageVO getPackage(Long packageNo);

    /* 확정/보관 */
    void confirmDIY(String email, Long hall, Long studio, Long dress, Long makeup);
    void confirmPackage(String email, Long packageNo);
    List<PlanVO> getCommittedPlans(String email);
    int getCommittedPlanCount(String email);

    /* === 보강: 프리뷰/카드용 계산 === */
    List<PackagePreviewDTO> getPackagePreviews(Integer type);
    List<PlanCardDTO> getPlanCards(String email);
}
