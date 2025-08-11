package oww.banking.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SafeBoxVO {
	
	private int safeboxId;
	private int balance;
	private Timestamp createAt;
	
}
