package com.oww.oww1.VO;

import com.oww.oww1.VO.ProductVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVO {
    private int product_no;
    private String product_name;
    private int category;   // 0: hall, 1: studio, 2: dress, 3: makeup
    private int cost;
    private String address;
    private String description;
    private String img;
}


