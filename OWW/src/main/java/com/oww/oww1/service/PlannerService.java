package com.oww.oww1.service;

import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.VO.PlanCardDTO;
import com.oww.oww1.VO.ProductVO;

import java.util.List;

/**
 * 웨딩플래너 핵심 조회/확정 서비스 (단순 로직만)
 * - MSA/JWT 관련 로직 없음
 * - 마이페이지와 충돌하지 않도록 조회 범위만 제공
 */
public interface PlannerService {

    // 제품 조회(카테고리/검색/정렬)
    List<ProductVO> getProducts(Integer category, String q, String sort);

    // 제품 다건 조회(IN)
    List<ProductVO> getProductsByIds(List<Integer> ids);

    // 패키지 목록/단건
    List<PackageVO> getPackages(Integer type);
    PackageVO getPackage(int packageNo);

    // DIY/패키지 확정
    void confirmDIY(String email, int hall, int studio, int dress, int makeup);
    void confirmPackage(String email, int packageNo);

    // 확정 건수/카드형 목록
    int getCommittedPlanCount(String email);
    List<PlanCardDTO> getPlanCards(String email);
    
 // 컨트롤러가 요구하는 미리보기용. 템플릿이 VO를 그대로 쓰므로 VO 리스트 반환.
    List<PackageVO> getPackagePreviews(Integer type);
}
