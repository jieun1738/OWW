package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MonthlySalesVO {
    private String ym;     // "YYYY-MM"
    private long   amount; // 월 매출 합계
}
