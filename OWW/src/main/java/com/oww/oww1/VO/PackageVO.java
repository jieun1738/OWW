package com.oww.oww1.VO;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PackageVO {
    private int package_no;
    private int type;      // 0~4
    private int hall;
    private int studio;
    private int dress;
    private int makeup;
    private int discount;  // (추가된 컬럼)
}

