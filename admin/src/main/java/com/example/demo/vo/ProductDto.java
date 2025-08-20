package com.example.demo.vo;
import lombok.Data;

@Data
public class ProductDto {
    private Integer productNo;
    private String  productName;   
    private Integer category;     
    private Long    cost;
    private String  address;
    private String  description;
    private String  img;
}
