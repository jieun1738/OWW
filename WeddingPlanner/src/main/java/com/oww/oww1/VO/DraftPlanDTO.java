package com.wedding.oww.vo;

import java.time.Instant;

import lombok.Data;

//세션 전용 DTO
@Data
public class DraftPlanDTO {
    private String draftId;     // UUID
    private String userEmail;
    private String title;       // 사용자 임의 제목(선택)
    private Long planNo;        // (옵션) 불러오기 시 식별용

    // 선택 항목(카테고리별 1개씩)
    private Long hall;          // product_no
    private Long studio;
    private Long dress;
    private Long makeup;

    private Instant createdAt;
}
