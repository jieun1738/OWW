package com.oww.oww1.VO;

import lombok.Data;

/**
 * VIEW: package_admin_v 전용 VO
 * - 컬럼명 그대로 스네이크 케이스 사용
 * - 타입은 int / String 만 사용
 */
@Data
public class PlannerPackageAdminVVO {
    private int package_no;
    private int type;
    private int discount;

    private int hall_no;
    private String hall_name;
    private String hall_address;
    private int hall_cost;
    private String hall_img;

    private int studio_no;
    private String studio_name;
    private int studio_cost;

    private int dress_no;
    private String dress_name;
    private int dress_cost;

    private int makeup_no;
    private String makeup_name;
    private int makeup_cost;

    private int total_price;
    private int final_price;
}
