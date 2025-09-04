package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;

@Mapper
public interface PlanMapper {

    // 사용자 최종결정 플랜 목록
    List<PlanVO> findPlansByUser(@Param("userEmail") String userEmail);

    // 특정 플랜의 상품 목록
    List<ProductVO> findProductsByPlanNo(@Param("planNo") int planNo);

    // DIY 확정 insert (plan_no는 트리거에서 자동)
    int insertPlanDIY(
        @Param("userEmail") String userEmail,
        @Param("hall")      int hall,
        @Param("studio")    int studio,
        @Param("dress")     int dress,
        @Param("makeup")    int makeup
    );

    // 패키지 확정 insert
    int insertPlanFromPackage(
        @Param("userEmail") String userEmail,
        @Param("packageNo") int packageNo
    );

    // 먼저 plan_progress 삭제
    int deleteProgressByUser(@Param("userEmail") String userEmail);
    int deleteProgressByPlanNo(@Param("planNo") int planNo);

    // 그 다음 plan 삭제
    int deleteFinalByUser(@Param("userEmail") String userEmail);
    int deleteFinalByPlanNo(@Param("planNo") int planNo);
}
