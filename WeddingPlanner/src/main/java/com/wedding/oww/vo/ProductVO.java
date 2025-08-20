package com.wedding.oww.vo;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVO {
    private Long productNo;
    private String productName;
    private Integer category;   // 0: hall, 1: studio, 2: dress, 3: makeup
    private Integer cost;
    private String address;
    private String description;
    private String img;
}
