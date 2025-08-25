package com.oww.oww1.VO;

import lombok.Data;

/**
 * 확정 전 미리보기용 합성 DTO
 * - 패키지/DIY 공통으로 카드에 표시할 정보
 */
@Data
public class PlanCardDTO {
    private int hallNo;
    private String hallName;
    private String hallImg;
    private int hallCost;

    private int studioNo;
    private String studioName;
    private String studioImg;
    private int studioCost;

    private int dressNo;
    private String dressName;
    private String dressImg;
    private int dressCost;

    private int makeupNo;
    private String makeupName;
    private String makeupImg;
    private int makeupCost;

    private int packageNo;   // 패키지면 번호, DIY면 9999
    private int discount;    // %
    private int totalPrice;  // 합계
    private int finalPrice;  // 최종가
}
