package com.oww.oww1.VO;

import lombok.Data;

@Data
public class PackageCardVO {
    private int packageNo;
    private int type;        // 0~4
    private int discount;

    private int hallNo;
    private String hallName;
    private String hallAddress;
    private int hallCost;
    private String hallImg;

    private int studioNo;
    private String studioName;
    private int studioCost;

    private int dressNo;
    private String dressName;
    private int dressCost;

    private int makeupNo;
    private String makeupName;
    private int makeupCost;

    private int totalPrice;
    private int finalPrice;
}
