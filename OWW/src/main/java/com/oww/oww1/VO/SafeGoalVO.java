package com.oww.oww1.VO;

import lombok.Data;

/**
 * 세이프박스 목표 설정 VO
 */
@Data
public class SafeGoalVO {
    private Long goalId;        // 목표 ID (PK)
    private String userEmail;   // 사용자 이메일
    private Long targetAmount;  // 목표 금액
    private Long savedAmount;   // 현재 저축액
    private boolean regular;    // 정기저축 여부 (true/false)
}
