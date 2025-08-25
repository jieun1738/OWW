package com.example.loan.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.loan.vo.UserLoanVO;

@Mapper
public interface UserLoanMapper {
	int insertUserLoan(UserLoanVO loan);
	
	UserLoanVO getUserLoan(String UserEmail);
	
	int repaymentLoan(String UserEmail, int LoanCurrentAmount,int PaidAmount);
	
	int endRepayment(String UserEmail);
	
	int getloanexist(String UserEmail);

	int getloanapprove(String userEmail);
	
	int getcurrentAmount(String UserEmail);

	void updatePeriod(String UserEmail);

	void resetpaidmonthlyamount();

	int getpaidmonthlyamount(String UserEmail);
}
