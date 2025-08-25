package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;

/** 플래너 전용 MyBatis 매퍼 인터페이스 */
@Mapper
public interface PlannerMapper {
    int countPlans(String email);                // 사용자의 전체 플랜 개수
    PlanVO findRecentPlan(String email);         // 최근 확정 플랜 1건
    List<PackageVO> selectPackages();            // 패키지 목록
    List<ProductVO> selectProducts();            // 상품 목록
    List<PlanVO> selectTempPlans(String email);  // 임시 플랜 목록(DIY)
    PlanVO findFinalPlan(String email);          // 최종 확정 플랜 1건
}
