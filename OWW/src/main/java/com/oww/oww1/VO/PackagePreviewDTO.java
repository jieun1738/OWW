package com.oww.oww1.VO;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PackagePreviewDTO {
    private Long packageNo;
    private Integer type;          // 0~4
    private Integer discount;      // %
    private Integer total;         // 정가 합계
    private Integer discounted;    // 할인 적용 후
}
