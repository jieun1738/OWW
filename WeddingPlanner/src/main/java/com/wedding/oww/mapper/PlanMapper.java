package com.wedding.oww.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wedding.oww.vo.PlanVO;

@Mapper
public interface PlanMapper {
    int insert(PlanVO plan);

    List<PlanVO> findByUser(@Param("userEmail") String userEmail);

    PlanVO findById(@Param("planNo") Long planNo);

    int delete(@Param("planNo") Long planNo);

    int countByUser(@Param("userEmail") String userEmail);
    
    /* DIY 확정: 구성품 그대로 저장 (plan_no는 트리거로 AUTO) */
    int insertDIY(@Param("email") String email,
                  @Param("hall") Long hall,
                  @Param("studio") Long studio,
                  @Param("dress") Long dress,
                  @Param("makeup") Long makeup);

    /* 패키지 확정: 패키지 구성품을 함께 복사 저장 (조회/마이페이지 호환성 ↑) */
    int insertPackage(@Param("email") String email,
                      @Param("packageNo") Long packageNo);

    List<PlanVO> findCommittedByUser(@Param("email") String email);

    Integer countCommittedByUser(@Param("email") String email);
}
