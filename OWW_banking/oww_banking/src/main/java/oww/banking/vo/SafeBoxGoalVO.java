package oww.banking.vo;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class SafeBoxGoalVO {
	
	private int goalId;
	private String title;
	private int targetAmount;
	private Date startDate;
	private Date endDate;
	private String paymentType;
	private Timestamp createAt;
}
