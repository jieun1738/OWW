package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oww.oww1.VO.ConfirmView;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;

public interface PlanMapper {

    List<PlanVO> findPlansByUser(@Param("userEmail") String userEmail);

    List<ProductVO> findProductsByPlanNo(@Param("planNo") int planNo);
    
    ConfirmView selectLatestConfirmed(@Param("userEmail") String userEmail);

    int insertPlanDIY(@Param("userEmail") String userEmail,
                      @Param("hall") int hall,
                      @Param("studio") int studio,
                      @Param("dress") int dress,
                      @Param("makeup") int makeup);

    int insertPlanFromPackage(@Param("userEmail") String userEmail,
                              @Param("packageNo") int packageNo);
}
