package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductRankVO {
    private Integer rank;       // 1,2,3...
    private Long    productNo;
    private String  productName;
    private Integer salesCount; // 사용/판매 횟수
}
