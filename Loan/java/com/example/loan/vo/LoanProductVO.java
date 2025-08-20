package com.example.loan.vo;

public class LoanProductVO {
	private String LoanName;
	private String LoanExplanation;
	private int LoanLimit;
	private int LoanPeriod;
	private int LoanRepaymentType;
	private float InterestRate;
	
	public LoanProductVO() {}

	
	public LoanProductVO(String loanName, String loanExplanation, int loanLimit, int loanPeriod, int loanRepaymentType,
			float interestRate) {
		super();
		LoanName = loanName;
		LoanExplanation = loanExplanation;
		LoanLimit = loanLimit;
		LoanPeriod = loanPeriod;
		LoanRepaymentType = loanRepaymentType;
		InterestRate = interestRate;
	}
	public String getLoanName() {
		return LoanName;
	}
	public void setLoanName(String loanName) {
		LoanName = loanName;
	}
	public String getLoanExplanation() {
		return LoanExplanation;
	}
	public void setLoanExplanation(String loanExplanation) {
		LoanExplanation = loanExplanation;
	}
	
	public int getLoanLimit() {
		return LoanLimit;
	}
	public void setLoanLimit(int loanLimit) {
		LoanLimit = loanLimit;
	}
	public int getLoanPeriod() {
		return LoanPeriod;
	}
	public void setLoanPeriod(int loanPeriod) {
		LoanPeriod = loanPeriod;
	}
	public int getLoanRepaymentType() {
		return LoanRepaymentType;
	}
	public void setLoanRepaymentType(int loanRepaymentType) {
		LoanRepaymentType = loanRepaymentType;
	}
	public float getInterestRate() {
		return InterestRate;
	}
	public void setInterestRate(float interestRate) {
		InterestRate = interestRate;
	}
}
