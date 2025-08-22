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
	private int discount=0;
	
	private String pay_category_str;
	private String contract_category_str;
	
	public boolean getCategorytoString() {
		
		System.out.println(this.category);
	if(category==0) {
		this.pay_category_str="pay_hall";
		this.contract_category_str="contract_hall";
		return true;
	}else if(category==1) {
		this.pay_category_str="pay_stud";
		this.contract_category_str="contract_stud";
		return true;
	}else if(category==2) {
		this.pay_category_str="pay_dres";
		this.contract_category_str="contract_dres";
		return true;
	}else if(category==3) {
		this.pay_category_str="pay_make";
		this.contract_category_str="contract_make";
		return true;
	}else if(category==4) {//전체결제
		this.pay_category_str="pay_all";
		this.contract_category_str="contract_all";
		return true;
	}
		return false;	
	
	}
}
