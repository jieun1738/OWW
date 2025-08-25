package com.oww.oww1.VO;

import lombok.Data;

/** PRODUCT 테이블 VO (category: 0=hall,1=studio,2=dress,3=makeup) */
@Data
public class ProductVO {
    private int productNo;
    private String productName;
    private int category;
    private int cost;
    private String address;
    private String description;
    private String img;
}
