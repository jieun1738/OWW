package com.oww.oww1.VO;

import lombok.Data;

//사이드바 표시용 DTO
@Data
public class DraftItemDTO {
    private int category;     // 0~3
    private String category_name;  // 라벨
    private int product_no;
    private String product_name;
    private int cost;
    private String img;
}
