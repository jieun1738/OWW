package com.oww.oww1.VO;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 세이프박스 저축 내역 VO
 */
@Data
public class SafeSavingVO {
    private Long savingId;        // 저축 ID (PK)
    private String userEmail;     // 사용자 이메일
    private Long amount;          // 저축 금액
    private String memo;          // 메모
    private LocalDateTime savedAt; // 저장 시각
}
