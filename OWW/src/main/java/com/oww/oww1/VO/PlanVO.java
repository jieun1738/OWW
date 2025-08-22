package com.oww.oww1.VO;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PlanVO {
    private int plan_no;
    private String user_email;
    private int package_no;  // null이면 DIY
    private int hall;
    private int studio;
    private int dress;
    private int makeup;
}


