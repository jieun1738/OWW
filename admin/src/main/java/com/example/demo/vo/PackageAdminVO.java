package com.example.demo.vo;
import lombok.Data;

@Data
public class PackageAdminVO {
    private Integer packageNo;
    private Integer type;
    private Integer discount;

    private Integer hallNo;
    private String  hallName;
    private String  hallAddress;
    private Long    hallCost;
    private String  hallImg;

    private String  studioName;
    private String  dressName;
    private String  makeupName;

    private Long    totalPrice;
    private Long    finalPrice;
}
