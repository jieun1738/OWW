package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardKpiVO {
    private long   salesThisMonth;     // 이번달 매출(원) - plan_progress pay_* 합계
    private String salesMoMDeltaText;  // 전월 대비 증감(문구, 예: "+12%")
    private int    newUsers;           // 이번달 신규 회원 수
    private int    activeProducts;     // 활성 상품 수
    private int    openPlans;          // 진행 중 플랜 수(계약 미완료)
    private String openPlansDeltaText; // (옵션) 증감 문구
}
