package oww.banking.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SafeBoxTransactionVO {
	
	private int safeboxTxId;
	private String txType;
	private int amount;
	private Timestamp txDate;
}
