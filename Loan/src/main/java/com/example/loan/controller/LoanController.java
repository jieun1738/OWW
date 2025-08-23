package com.example.loan.controller;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.loan.service.LoanService;
import com.example.loan.util.jwtutil;
import com.example.loan.vo.LoanProductVO;
import com.example.loan.vo.UserLoanVO;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoanController {
	
	@Autowired
	private LoanService loanservice;
	
	@Autowired
	private jwtutil jwtutil;
	
	/*
	 * @GetMapping("/loanmain") public String LoanMain(Model model) throws
	 * StreamReadException, DatabindException, IOException { ArrayList<String>
	 * products = loanservice.getloanmain(); model.addAttribute("products",
	 * products); return "LoanMain"; }
	 */
	
	@GetMapping("/loandetail")
	public String Loandetail(@RequestParam("loanName") String loanName, Model model) throws StreamReadException, DatabindException, IOException {
		
		LoanProductVO loandetails = loanservice.getloandetail(loanName);
		model.addAttribute("loandetails", loandetails);
		
		return "LoanDetail";
		
	}
	
	@GetMapping("searchloan")
	public String searchloan(@RequestParam("search") String search, @RequestParam String lastUrl,Model model) throws StreamReadException, DatabindException, IOException {
		ArrayList<String> products = loanservice.searchloan(search);
		model.addAttribute("products", products);
		
		if ("LoanMain".equals(lastUrl)) {
			return "LoanMain";
		}else if("LoanComparison".equals(lastUrl)){
			return "LoanComparison";
		}else {
			return "LoanMain";
		}
		
	}
	
	@GetMapping("/loancomparison")
	public String LoanCompraison(Model model) throws StreamReadException, DatabindException, IOException {
		ArrayList<String> products = loanservice.getloanmain();
		model.addAttribute("products", products);
		return "LoanComparison";
	}
	@GetMapping("/loancomparisonADD")
	public String LoanCompraisonadd(@RequestParam("loanName") String loanName,Model model, HttpSession session) throws StreamReadException, DatabindException, IOException {
		ArrayList<String> products = loanservice.getloanmain();
		model.addAttribute("products", products);
		
		LoanProductVO loandetails = loanservice.getloandetail(loanName);
		if(session.getAttribute("loandetails1") != null) {
			if(session.getAttribute("loandetails2")!= null) {
				
			}else {
				session.setAttribute("loandetails2", loandetails);
			}
		}else {
			session.setAttribute("loandetails1", loandetails);
		}
		
		
		return "LoanComparison";
	}
	
	@PostMapping("/loancomparisonDEL1")
	public String loancomparisonDEL1(HttpSession session,Model model) throws StreamReadException, DatabindException, IOException {
		ArrayList<String> products = loanservice.getloanmain();
		model.addAttribute("products", products);
		
		session.removeAttribute("loandetails1");
		return "LoanComparison";
	}
	@PostMapping("/loancomparisonDEL2")
	public String loancomparisonDEL2(HttpSession session,Model model) throws StreamReadException, DatabindException, IOException {
		ArrayList<String> products = loanservice.getloanmain();
		model.addAttribute("products", products);
		
		session.removeAttribute("loandetails2");
		return "LoanComparison";
	}
	
	@GetMapping("/insertloan")
	public String insertloan(@RequestParam("loanamount") int loanamount,@RequestParam("loanperiod") int loanperiod,
			@RequestParam("loanrepaymenttype") int loanrepaymenttype, @RequestParam("interestrate") double interestrate,@RequestParam("loanname") String loanname
			,HttpServletRequest request) throws Exception
	{
		
		 String token = null;
		    if (request.getCookies() != null) {
		        for (Cookie cookie : request.getCookies()) {
		            if ("jwt-token".equals(cookie.getName())) {
		                token = cookie.getValue();
		                break;
		            }
		        }
		    }

		    if (token == null) {
		        throw new IllegalArgumentException("JWT 토큰이 없습니다.");
		    }
		    
		Claims claims = jwtutil.validateAndGetClaims(token);
		String useremail = claims.getSubject();
		
		loanservice.insertloan(useremail,loanname,loanamount,loanperiod,loanrepaymenttype,interestrate);
		
		return "LoanComplete";
		
	}
	
	@GetMapping("/repayment")
	public String repayment(RedirectAttributes redirectAttributes ,Model model,HttpServletRequest request){//여기에 토큰에서 이메일 정보 넣어야함
		String useremail = request.getHeader("X-useremail");
		int approve = loanservice.getloanapprove(useremail);
		if(approve == 0) {
			model.addAttribute("infoMessage", "상환할 대출이 없습니다.");
			return "Repayment";
		}
		UserLoanVO userloan = loanservice.getuserloan(useremail);
		model.addAttribute("userloan",userloan);
		return "Repayment";
	}
	
	@GetMapping("/repaymentloan")
	public String repaymentloan(@RequestParam("paidamaount") String paidamaount,RedirectAttributes redirectAttributes,Model model,HttpServletRequest request) 
	{
		
			int paidamaountint = Integer.parseInt(paidamaount);
			String useremail = request.getHeader("X-useremail");
			//여기에 계좌 잔액 가져와서 뺀 금액 다시 돌려보내야 함
			
			//
			loanservice.repaymentloan(paidamaountint,useremail);
			UserLoanVO userloan = loanservice.getuserloan(useremail);
			model.addAttribute("userloan",userloan);
		return "Repayment";
	}
	
	@GetMapping("/costcalculate")
	public String costcalculate(Model model,HttpServletRequest request) {
		
		String useremail = request.getHeader("X-useremail");
		
		double monthlyinstellment = loanservice.sumMonthlyInstallment(useremail);
		
		model.addAttribute("monthlyinstellment",monthlyinstellment);
		
		return "CostCalculate";
		
	}
	
	@GetMapping("/costcalculateresult")
	public String costcalculateresult(@RequestParam("earnings")int earnings,@RequestParam("monthlyinstallment")int monthlyinstallment,@RequestParam("cost") int cost,Model model) {
		
		int restcost = loanservice.costcalculate(earnings,monthlyinstallment,cost);
		
		model.addAttribute("restcost",restcost);
		
		return "CostCalculate";
		
	}
	
}
