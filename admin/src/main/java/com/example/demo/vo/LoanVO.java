package com.example.demo.vo;

import java.util.Date;

import lombok.Data;

@Data
public class LoanVO {
    private Long   applicationId;  // application_id
    private String userEmail;      // user_email
    private String productName;    // product_name
    private Long   amount;         // amount
    private Integer termMonths;    // term_months
    private String status;         // PENDING / APPROVED / REJECTED
    private Date   requestedAt;    // requested_at
    private Date   decidedAt;      // decided_at
    private String decidedBy;      // decided_by
    private String memo;           // memo
	public void setLoanNo(Long id) {
	
		
	}
}
