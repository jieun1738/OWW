package com.oww.oww1.VO;

import lombok.Data;

/**
 * PLAN 테이블 VO
 * - packageNo: 패키지면 실제 번호, DIY 확정이면 9999
 * - 조회 시 NVL(package_no,0)로 0 반환하도록 매퍼에서 처리
 */
@Data
public class PlanVO {
    private int planNo;
    private String userEmail;
    private int packageNo;
    private int hall;
    private int studio;
    private int dress;
    private int makeup;
}
