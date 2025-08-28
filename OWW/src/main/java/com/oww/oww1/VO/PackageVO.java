// src/main/java/com/oww/oww1/vo/PackageVO.java
package com.oww.oww1.VO;

import lombok.Data;

//PackageVO (패키지 화면/확인용 파생 필드도 스네이크로)
@Data
public class PackageVO {
	private int package_no;
	private int type;
	private int hall;
	private int studio;
	private int dress;
	private int makeup;
	private int discount; // %
// 조인표시용
	private String hall_name;
	private int hall_cost;
	private String hall_img;
	private String studio_name;
	private int studio_cost;
	private String dress_name;
	private int dress_cost;
	private String makeup_name;
	private int makeup_cost;
	private int total_price; // 합계
	private int final_price; // 할인적용가

	// --- 어댑터: 카멜 방식 접근 지원 ---
	public int getPackageNo() {
		return package_no;
	}

	public void setPackageNo(int packageNo) {
		this.package_no = packageNo;
	}

	public String getHallName() {
		return hall_name;
	}

	public void setHallName(String hallName) {
		this.hall_name = hallName;
	}

	public int getHallCost() {
		return hall_cost;
	}

	public void setHallCost(int hallCost) {
		this.hall_cost = hallCost;
	}

	public String getHallImg() {
		return hall_img;
	}

	public void setHallImg(String hallImg) {
		this.hall_img = hallImg;
	}

	public String getStudioName() {
		return studio_name;
	}

	public void setStudioName(String studioName) {
		this.studio_name = studioName;
	}

	public int getStudioCost() {
		return studio_cost;
	}

	public void setStudioCost(int studioCost) {
		this.studio_cost = studioCost;
	}

	public String getDressName() {
		return dress_name;
	}

	public void setDressName(String dressName) {
		this.dress_name = dressName;
	}

	public int getDressCost() {
		return dress_cost;
	}

	public void setDressCost(int dressCost) {
		this.dress_cost = dressCost;
	}

	public String getMakeupName() {
		return makeup_name;
	}

	public void setMakeupName(String makeupName) {
		this.makeup_name = makeupName;
	}

	public int getMakeupCost() {
		return makeup_cost;
	}

	public void setMakeupCost(int makeupCost) {
		this.makeup_cost = makeupCost;
	}

	public int getTotalPrice() {
		return total_price;
	}

	public void setTotalPrice(int totalPrice) {
		this.total_price = totalPrice;
	}

	public int getFinalPrice() {
		return final_price;
	}

	public void setFinalPrice(int finalPrice) {
		this.final_price = finalPrice;
	}
}