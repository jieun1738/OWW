package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.PackagePreviewDTO;
import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.VO.PlanCardDTO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;

public interface PlannerService {

    /* 제품/패키지 조회 */
    List<ProductVO> getProducts(int category, String q, String sort); // category -1이면 전체
    List<ProductVO> getProductsByIds(List<Integer> ids);
    List<PackageVO> getPackages(int type); // type -1이면 전체
    PackageVO getPackage(int packageNo);

    /* 확정/보관 */
    void confirmDIY(String email, int hall, int studio, int dress, int makeup); // 0이면 미선택
    void confirmPackage(String email, int packageNo);
    List<PlanVO> getCommittedPlans(String email);
    int getCommittedPlanCount(String email);

    /* 프리뷰/카드 */
    List<PackagePreviewDTO> getPackagePreviews(int limit);
    List<PlanCardDTO> getPlanCards(String email);

    /* 최근 1건 */
    PlanVO findRecentPlan(String email);
}
