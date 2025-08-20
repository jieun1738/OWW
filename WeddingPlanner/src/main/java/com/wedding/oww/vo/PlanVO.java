package com.wedding.oww.vo;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PlanVO {
    private Long planNo;
    private String userEmail;
    private Long packageNo;  // null이면 DIY
    private Long hall;
    private Long studio;
    private Long dress;
    private Long makeup;
}
