package com.example.loan.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.loan.mapper.UserLoanMapper;
import com.example.loan.vo.LoanProductVO;
import com.example.loan.vo.UserLoanVO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class LoanServiceImpl implements LoanService {


	@Autowired
	private UserLoanMapper userLoanMapper;
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	private final String filePath = "src/main/resources/data/LoanProducts.json";
	
	/*
	 * @Override public ArrayList getloanmain() { return
	 * loanProductMapper.getAllLoan(); // TODO Auto-generated method stub
	 * 
	 * }
	 */
	private List<LoanProductVO> getAllLoanProducts() throws StreamReadException, DatabindException, IOException{
        return objectMapper.readValue(
                new File(filePath),
                new TypeReference<List<LoanProductVO>>() {}
        );
    }
	
	@Override
	public ArrayList<String> getloanmain() throws StreamReadException, DatabindException, IOException {
		 List<LoanProductVO> allProducts = getAllLoanProducts();
	        ArrayList<String> productNames = new ArrayList<>();

	        for (LoanProductVO product : allProducts) {
	            productNames.add(product.getLoanName());
	        }

	        return productNames;
	}
	
	/*
	 * @Override public LoanProductVO getloandetail(String loanName) { // TODO
	 * Auto-generated method stub return loanProductMapper.getLoan(loanName); }
	 */
	
	@Override
	public LoanProductVO getloandetail(String loanName) throws StreamReadException, DatabindException, IOException {
		// TODO Auto-generated method stub
		return getAllLoanProducts().stream()
                .filter(p -> p.getLoanName().equalsIgnoreCase(loanName))
                .findFirst()
                .orElse(null);
	}
	

	@Override
	public ArrayList<String> searchloan(String search) throws StreamReadException, DatabindException, IOException {
		// TODO Auto-generated method stub
		List<LoanProductVO> allProducts = getAllLoanProducts();
	    ArrayList<String> matchedNames = new ArrayList<>();

	    for (LoanProductVO product : allProducts) {
	        if (product.getLoanName().contains(search)) { // 부분 일치
	            matchedNames.add(product.getLoanName());
	        }
	    }
	    return matchedNames;
	}

	public long sumMonthlyInstallment(String useremail) {

	    UserLoanVO userloan = userLoanMapper.getUserLoan(useremail);

	    double interestRate = userloan.getInterestRate();
	    int loanRepaymentType = userloan.getLoanRepaymentType();
	    int loanPeriod = userloan.getLoanPeriod();
	    int loanAmount = userloan.getLoanAmount();
	    int currentMonth = userloan.getLoanCurrentPeriod();

	    double monthlyRate = interestRate / 12 / 100; // 월 이자율
	    double payment = 0;

	    if (loanRepaymentType == 0) {
	        // 원리금균등상환 계산
	        double numerator = monthlyRate * Math.pow(1 + monthlyRate, loanPeriod);
	        double denominator = Math.pow(1 + monthlyRate, loanPeriod) - 1;
	        double fixedMonthlyPayment = loanAmount * (numerator / denominator);
	        payment = fixedMonthlyPayment;

	    } else if (loanRepaymentType == 1) {
	        // 원금균등상환 계산
	        double monthlyPrincipal = (double) loanAmount / loanPeriod;
	        double remainingPrincipalForMonth = loanAmount - monthlyPrincipal * (currentMonth - 1);
	        double interest = remainingPrincipalForMonth * monthlyRate;

	        double principalPayment = monthlyPrincipal;
	        if (remainingPrincipalForMonth < monthlyPrincipal) {
	            principalPayment = remainingPrincipalForMonth;
	        }
	        payment = principalPayment + interest;
	    }

	    // 소수점 반올림 (정수로

	    long result = Math.round(payment);
	    return result;
	}


	@Override
	public void insertloan(String useremail,String loanname, int loanamountint, int loanperiodint, int loanrepaymenttypeint,
			double interestratedouble) {
		  UserLoanVO userloanvo = new UserLoanVO();
		userloanvo.setUserEmail(useremail);
	    userloanvo.setLoanName(loanname);
	    userloanvo.setLoanAmount(loanamountint);
	    userloanvo.setLoanPeriod(loanperiodint);
	    userloanvo.setLoanRepaymentType(loanrepaymenttypeint);
	    userloanvo.setInterestRate(interestratedouble);
		userLoanMapper.insertUserLoan(userloanvo);
		
	}

	@Override
	public int getloanapprove(String useremail) {
		// TODO Auto-generated method stub
	int exist = userLoanMapper.getloanexist(useremail);
	int approve =0;
	if (exist == 1) {
		approve = userLoanMapper.getloanapprove(useremail);
	}else {
		return approve;
	}
		
		return approve;
	}

	@Override
	public void repaymentloan(int paidamount,String useremail) {
		// TODO Auto-generated method stub
		int currentAmount = userLoanMapper.getcurrentAmount(useremail);
		currentAmount = currentAmount + paidamount;
		userLoanMapper.repaymentLoan(useremail, currentAmount);
		userLoanMapper.updatePeriod(useremail);
	}

	@Override
	public UserLoanVO getuserloan(String useremail) {
		 
		return userLoanMapper.getUserLoan(useremail);
	}

	@Override
	public double costcalculate(int earningsint, double monthlyinstallmentint, int costint) {	
		
		return earningsint -monthlyinstallmentint-costint;
	}

	@Override
	@Scheduled(cron = "0 0 0 * * *")
    @Transactional
	public void resetpaidmonthlyamount() {
		// TODO Auto-generated method stub
		LocalDate today = LocalDate.now();
		if (today.getDayOfMonth() == 1) {
	userLoanMapper.resetpaidmonthlyamount();
		}
	}


	

	
}
