package com.wedding.oww.vo;

import lombok.Data;

//사이드바 표시용 DTO
@Data
public class DraftItemDTO {
    private Integer category;     // 0~3
    private String categoryName;  // 라벨
    private Long productNo;
    private String productName;
    private Integer cost;
    private String img;
}
