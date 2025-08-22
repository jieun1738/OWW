package com.oww.oww1.VO;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class PlanCardDTO {
    private Long planNo;
    private boolean packageBased;  // packageNo != null
    private Integer total;         // 구성 합계
    private String thumbnailImg;   // 대표 썸네일(우선: 홀 이미지, 없으면 첫 품목)
}
