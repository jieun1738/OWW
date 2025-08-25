package com.oww.oww1.VO;

import lombok.Data;

/**
 * 패키지 카드 미리보기용 DTO
 * - 썸네일: hall 이미지 사용
 * - title: "패키지 #번호"
 */
@Data
public class PackagePreviewDTO {
    private int packageNo;
    private int type;
    private String title;
    private String thumbnailImg;
    private int discount;
    private int totalPrice;
    private int finalPrice;
}
