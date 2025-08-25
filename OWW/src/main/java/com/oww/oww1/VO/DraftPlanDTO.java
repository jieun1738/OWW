package com.oww.oww1.VO;

import lombok.Data;

/**
 * DIY 임시 플랜(세션 보관/전송용)
 * 모두 int로 통일
 */
@Data
public class DraftPlanDTO {
    private int hall;
    private int studio;
    private int dress;
    private int makeup;
}
