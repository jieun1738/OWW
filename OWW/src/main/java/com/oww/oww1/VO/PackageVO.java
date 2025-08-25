package com.oww.oww1.VO;

import lombok.Data;

/** PACKAGE 테이블 원본 VO */
@Data
public class PackageVO {
    private int packageNo;
    private int type;    // 0~4
    private int hall;
    private int studio;
    private int dress;
    private int makeup;
    private int discount; // DEFAULT 0
}
