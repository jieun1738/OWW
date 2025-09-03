package com.oww.oww1.VO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SafeBoxHistoryVO {
	int payment_id;
	int goal_id;
	BigDecimal amount;
	LocalDate payment_date;
	String user_email;
	
}
