package oww.banking.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TransferHistoryVO {
	private int tx_id;
	private String tx_type;
	private int amount;
	private String memo;
	private Timestamp tx_date;
	

}
