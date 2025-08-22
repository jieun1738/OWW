package com.oww.oww1.service;

import com.oww.oww1.VO.*;

import java.util.List;

public interface PlannerService {

    /* 제품/패키지 조회 */
    List<ProductVO> getProducts(Integer category, String q, String sort);
    List<ProductVO> getProductsByIds(List<Integer> ids);
    List<PackageVO> getPackages(Integer type);
    PackageVO getPackage(int packageNo);

    /* 확정/보관 */
    void confirmDIY(String email, int i, int j, int k, int l);
    void confirmPackage(String email, int packageNo);
    List<PlanVO> getCommittedPlans(String email);
    int getCommittedPlanCount(String email);

    /* === 보강: 프리뷰/카드용 계산 === */
    List<PackagePreviewDTO> getPackagePreviews(Integer type);
    List<PlanCardDTO> getPlanCards(String email);
}
