package oww.banking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankingController {
	
	 @GetMapping("/")
	    public String main() {
	        return "banking_main"; 
	    }
	 
	 @GetMapping("/banking_main")
	    public String banking_main() {
	        return "banking_main"; 
	    }
	
	 @GetMapping("/createAccount")
	    public String kaja3() {
	        return "banking_createAccount"; 
	    }
	    
	    @GetMapping("/banking_safebox")
	    public String kaja4() {
	        return "banking_safebox"; 
	    }
	    
	    @GetMapping("/banking_transfer_1")
	    public String transfer_1() {
	        return "banking_transfer_1"; 
	    }

	    @GetMapping("/banking_transfer_2")
	    public String transfer_2() {
	        return "banking_transfer_2"; 
	    }
	    
	    @GetMapping("/banking_transfer_3")
	    public String transfer_3() {
	        return "banking_transfer_3"; 
	    }
}
