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

	// --- 어댑터: 카멜 방식 접근 지원 ---
	public int getPlanNo() {
		return plan_no;
	}

	public void setPlanNo(int planNo) {
		this.plan_no = planNo;
	}

	public String getUserEmail() {
		return user_email;
	}

	public void setUserEmail(String userEmail) {
		this.user_email = userEmail;
	}

	public int getPackageNo() {
		return package_no;
	}

	public void setPackageNo(int packageNo) {
		this.package_no = packageNo;
	}
}
