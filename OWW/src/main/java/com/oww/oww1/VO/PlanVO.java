// src/main/java/com/oww/oww1/vo/PlanVO.java
package com.oww.oww1.VO;

import lombok.Data;

@Data
public class PlanVO {
	private int plan_no;
	private String user_email; // 외래키 ()
	private int package_no; // 패키지 확정 시 번호, DIY면 9999
	private int hall;
	private int studio;
	private int dress;
	private int makeup;
}
