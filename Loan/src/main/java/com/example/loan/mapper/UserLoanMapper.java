package com.example.loan.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.loan.vo.UserLoanVO;

@Mapper
public interface UserLoanMapper {
	int insertUserLoan();
	
	UserLoanVO getUserLoan(String UserEmail);
	
	int repaymentLoan(String UserEmail,  int LoanCurrentAmount);
	
	int endRepayment(String UserEmail);
	
	int getloanexist(String UserEmail);

	int getloanappreove(String userEmail);
	
	int getcurrentAmount(String UserEmail);

	void updatePeriod(String UserEmail);

	void resetpaidmonthlyamount();
}
