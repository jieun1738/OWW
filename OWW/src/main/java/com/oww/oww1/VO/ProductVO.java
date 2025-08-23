// src/main/java/com/oww/oww1/vo/ProductVO.java
package com.oww.oww1.VO;
import lombok.Data;
//ProductVO
@Data
public class ProductVO {
private int product_no;
private String product_name;  // <- getProduct_name() 제공
private int category;         // 0 hall, 1 studio, 2 dress, 3 makeup
private int cost;
private String address;
private String description;
private String img;
}

