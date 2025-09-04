package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oww.oww1.VO.PlannerPackageAdminVVO;

@Mapper
public interface Plannermapper {

    // =========================
    // Category(패키지) 화면 전용
    // =========================

    /** 카테고리(패키지) 카드 목록: package_admin_v 전체 */
    List<PlannerPackageAdminVVO> select_package_admin_list();

    /** 단일 패키지 카드 상세 */
    PlannerPackageAdminVVO select_package_admin_one(@Param("package_no") int package_no);
}
