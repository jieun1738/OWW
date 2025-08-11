package oww.banking.vo;

import java.sql.Date;

import lombok.Data;

@Data
public class SafaBoxHistoryVO {

	private int paymentId;
	private int amount;
	private Date paymentDate;
}
