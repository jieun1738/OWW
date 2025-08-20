package com.oww.oww1.VO;

import lombok.Data;

@Data
public class PaymentDTO {
	private String orderId; // JSON의 orderId와 매핑
	private int amount; // JSON의 amount와 매핑
	private String itemName; // JSON의 itemName과 매핑
	private String user_email;
	private int category;//0: hall, 1: studio, 2: dress, 3: makeup
	private int plan_no;
	
	private String category_str;
	
	public boolean getCategorytoString() {
	if(category==0) {
		this.category_str="pay_hall";
		return true;
	}else if(category==1) {
		this.category_str="pay_stud";
		return true;
	}else if(category==2) {
		this.category_str="pay_dres";
		return true;
	}else if(category==3) {
		this.category_str="pay_make";
		return true;
	}else 
		return false;	
	
	}
}
