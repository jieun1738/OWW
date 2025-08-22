
package com.example.demo.vo;

import lombok.Data;

@Data
public class ProductVO {
 private Long productNo;      // product_no (PK)
 private String productName;  // 상품명
 private String category;     // 카테고리 (varchar)
 private Integer cost;        // 가격
 private String address;      // 주소 (예: 예식장)
 private String img;          // 이미지 경로
 private String description;  // 설명
}
