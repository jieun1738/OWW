package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.PlannerPackageAdminVVO;



public interface Plannerservice {

    // =========================
    // Category(패키지) 화면 전용
    // =========================
    List<PlannerPackageAdminVVO> get_category_cards();
    PlannerPackageAdminVVO get_category_card(int package_no);
}
