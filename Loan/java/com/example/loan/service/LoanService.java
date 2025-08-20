package com.example.loan.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.loan.vo.LoanProductVO;
import com.example.loan.vo.UserLoanVO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

public interface LoanService {


	ArrayList<String> getloanmain() throws StreamReadException, DatabindException, IOException;
	
	
	LoanProductVO getloandetail(String loanName) throws StreamReadException, DatabindException, IOException;

	ArrayList<String> searchloan(String search) throws StreamReadException, DatabindException, IOException;

	double sumMonthlyInstallment(String useremail);

	void insertloan(String loanname, String loanname2, int loanamountint, int loanperiodint, int loanrepaymenttypeint,
			double interestratedouble);

	int getuserappove(String useremail);


	UserLoanVO getuserloan(String useremail);


	void repaymentloan(int paidamaountint, String useremail);


	int costcalculate(int earningsint, int monthlyinstallmentint, int costint);

	void resetpaidmonthlyamount();

}
