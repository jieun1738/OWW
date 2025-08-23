// src/main/java/com/oww/oww1/vo/PackageVO.java
package com.oww.oww1.VO;
import lombok.Data;
//PackageVO (패키지 화면/확인용 파생 필드도 스네이크로)
@Data
public class PackageVO {
private int package_no; private int type;
private int hall; private int studio; private int dress; private int makeup;
private int discount;         // %
// 조인표시용
private String hall_name;     private int hall_cost;     private String hall_img;
private String studio_name;   private int studio_cost;
private String dress_name;    private int dress_cost;
private String makeup_name;   private int makeup_cost;
private int total_price;      // 합계
private int final_price;      // 할인적용가
}