package com.oww.oww1.VO;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PackageVO {
    private Long packageNo;
    private Integer type;      // 0~4
    private Long hall;
    private Long studio;
    private Long dress;
    private Long makeup;
    private Integer discount;  // (추가된 컬럼)
}
