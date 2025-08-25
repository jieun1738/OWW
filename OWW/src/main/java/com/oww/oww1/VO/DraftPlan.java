package com.oww.oww1.VO;

import lombok.Data;

/* DIY 임시 플랜(세션 보관). category별 1개씩 선택 */
@Data
public class DraftPlan {
    private int hall;    // 0=없음
    private int studio;
    private int dress;
    private int makeup;
    private String title; // 옵션
}
