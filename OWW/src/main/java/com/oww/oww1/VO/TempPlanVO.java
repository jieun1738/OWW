package com.oww.oww1.VO;

import lombok.Data;

@Data
public class TempPlanVO {
    private String tempId;
    private String title;
    private String savedAt;

    private ProductVO hall;
    private ProductVO studio;
    private ProductVO dress;
    private ProductVO makeup;

    private int totalCost;
}
