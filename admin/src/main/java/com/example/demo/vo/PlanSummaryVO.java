package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PlanSummaryVO {
    private Long   planNo;
    private String userEmail;
    private Long   packageNo;
    private Long   hall;
    private Long   studio;
    private Long   dress;
    private Long   makeup;
}
