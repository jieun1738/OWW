package com.oww.oww1.VO;

import lombok.Data;

@Data
public class PaymentDTO {
	private String orderId; // JSON의 orderId와 매핑
	private int amount; // JSON의 amount와 매핑
	private String itemName; // JSON의 itemName과 매핑
}
