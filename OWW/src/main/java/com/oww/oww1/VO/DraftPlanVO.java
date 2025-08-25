package com.oww.oww1.VO;

import lombok.Data;

/** 임시 플랜(세션 보관용) */
@Data
public class DraftPlanVO {
    private String name; // 표시용 이름
    private int hall;
    private int studio;
    private int dress;
    private int makeup;
}
