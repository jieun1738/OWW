package com.oww.oww1.VO;

import lombok.Data;

@Data
public class BudgetVO {
	
	String user_email;
	///////////////////////////////////////
	////토큰에서 email///////////////////

	int budget_no, hall, studio, dress, makeup;
	int dvd, makeup_parents, ring, hair, skincare, hanbok, honeymoon, invitation;
	
	public int getAmount() {
		// TODO Auto-generated method stub
		int result = hall + studio + dress + makeup + dvd + makeup_parents + ring + hair + skincare + hanbok + honeymoon + invitation;
		return result;
	}
}
