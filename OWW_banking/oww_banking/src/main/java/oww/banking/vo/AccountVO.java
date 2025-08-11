package oww.banking.vo;

import java.security.Timestamp;

import lombok.Data;

@Data
public class AccountVO {

	private int accountId;
	private String userEmail;
	private String accountName;
	private int balance;
	private Timestamp createdAt;
}
