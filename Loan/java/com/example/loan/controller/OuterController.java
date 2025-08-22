package com.example.loan.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.loan.vo.BankingVO;

@RestController

public class OuterController {
	 private final RestTemplate restTemplate;

	 public OuterController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	 }
	 
	 	@GetMapping("/{userEmail}/getaccount")
	 	public ResponseEntity<String> getAccount() {
	 		String loanName = "getAccount";
	        String url = "http://banking-service/" + loanName;
	       //여기는 나중에 뱅킹 구현되면 구현해야함 
	       BankingVO account = restTemplate.getForObject(url, BankingVO.class);
	        
			return ResponseEntity.ok("요청이 완료되었습니다.");
	 	}
	 	
	 	@PutMapping("/{userEmail}/updateAccount")//이건 banking에 보내는 쪽
	 	public ResponseEntity<String> updateAccount(@RequestParam("/setAccountamount") int Accountamount,
	 			@RequestParam("/setAccountnumber") int Accountnumber) 
	 	{
	 		
	 		BankingVO bankingVO = null;
	 		bankingVO.setAccountamount(Accountamount);
	 		bankingVO.setAccountnumber(Accountnumber);
	 		String url = "http://banking-service/" + "updateAccount";
	        restTemplate.put(url, bankingVO);//뱅킹에 보내는 명령
	         
			return ResponseEntity.ok("요청이 완료되었습니다.");
	 		
	 	}
	 
}

