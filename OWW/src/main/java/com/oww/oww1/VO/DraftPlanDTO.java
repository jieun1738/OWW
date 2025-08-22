package com.oww.oww1.VO;

import java.time.Instant;

import lombok.Data;

//세션 전용 DTO
@Data
public class DraftPlanDTO {
    private String draftId;     // UUID
    private String userEmail;
    private String title;       // 사용자 임의 제목(선택)
    private int planNo;        // (옵션) 불러오기 시 식별용

    // 선택 항목(카테고리별 1개씩)
    private int hall;          // product_no
    private int studio;
    private int dress;
    private int makeup;

    private Instant createdAt;
}
