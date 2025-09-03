package com.example.loan.vo;

public class UserLoanVO {
	
	
	private String UserEmail;
	private String LoanName;
	private int LoanAmount;
	private int LoanCurrentAmount;
	private int LoanPeriod;
	private int LoanCurrentPeriod;
	private int LoanRepaymentType;
	private int Approval;
	private double InterestRate;
	private int paidmonthlyamount;

	
	 public String getUserEmail() { return UserEmail; } public void
	 setUserEmail(String userEmail) { UserEmail = userEmail; }
	 
	
	public String getLoanName() {
		return LoanName;
	}
	public void setLoanName(String loanName) {
		LoanName = loanName;
	}
	public int getLoanAmount() {
		return LoanAmount;
	}
	public void setLoanAmount(int loanAmount) {
		LoanAmount = loanAmount;
	}
	public int getLoanCurrentAmount() {
		return LoanCurrentAmount;
	}
	public void setLoanCurrentAmount(int loanCurrentAmount) {
		LoanCurrentAmount = loanCurrentAmount;
	}
	public int getLoanPeriod() {
		return LoanPeriod;
	}
	public void setLoanPeriod(int loanPeriod) {
		LoanPeriod = loanPeriod;
	}
	public int getLoanCurrentPeriod() {
		return LoanCurrentPeriod;
	}
	public void setLoanCurrentPeriod(int loanCurrentPeriod) {
		LoanCurrentPeriod = loanCurrentPeriod;
	}
	public int getLoanRepaymentType() {
		return LoanRepaymentType;
	}
	public void setLoanRepaymentType(int loanRepaymentType) {
		LoanRepaymentType = loanRepaymentType;
	}
	public int getApproval() {
		return Approval;
	}
	public void setApproval(int approval) {
		Approval = approval;
	}
	public double getInterestRate() {
		return InterestRate;
	}
	public void setInterestRate(double interestRate) {
		InterestRate = interestRate;
	}
	public int getPaidmonthlyamount() {
		return paidmonthlyamount;
	}
	public void setPaidmonthlyamount(int paidmonthlyamount) {
		this.paidmonthlyamount = paidmonthlyamount;
	}

}
