package com.example.demo.vo;

import lombok.Data;

@Data
public class ProductVO {
    private Integer productNo;     // PK 
    private String  productName;   // 업체/상품명
    private Integer category;      // 0: hall, 1: studio, 2: dress, 3: makeup
    private Long    cost;          // 가격
    private String  address;       // 지역 API에서 받은 값 저장
    private String  img;           // 이미지 경로/URL
    private String  description;   // 설명
}
