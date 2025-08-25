package com.oww.oww1.VO;

import lombok.Data;

/** package_admin_v 뷰 VO (카드/상세/가격 집계) */
@Data
public class PackageAdminViewVO {
    private int packageNo;
    private int type;
    private int discount;

    private int hallNo;
    private String hallName;
    private String hallVendor;
    private String hallAddress;
    private int hallCost;
    private String hallImg;

    private int studioNo;
    private String studioName;
    private String studioVendor;
    private int studioCost;

    private int dressNo;
    private String dressName;
    private String dressVendor;
    private int dressCost;

    private int makeupNo;
    private String makeupName;
    private String makeupVendor;
    private int makeupCost;

    private int totalPrice;
    private int finalPrice;
}
