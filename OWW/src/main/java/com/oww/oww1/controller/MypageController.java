package com.oww.oww1.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.oww.oww1.OwwApplication;
import com.oww.oww1.VO.PaymentDTO;
import com.oww.oww1.VO.PlanProgressVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.service.BudgetService;


@Controller
public class MypageController {

    private final OwwApplication owwApplication;
	@Autowired
	BudgetService budservice;
	

    MypageController(OwwApplication owwApplication) {
        this.owwApplication = owwApplication;
    }

	@GetMapping("/mypage")
	public String goMypage(Model model) {
		String user_email = "user1@example.com";
		model.addAttribute("totalBudget", budservice.getBudget(user_email));
		int sumBudget=budservice.sumBudget(user_email);
		model.addAttribute("sumBudget", sumBudget);	
		List<ProductVO> user_product = budservice.getProductInfo(user_email);
		int total_cost =0;
		for (ProductVO imsi :user_product) {
			
			total_cost += imsi.getCost();
			switch (imsi.getCategory()) {
			case 0:
				model.addAttribute("hallInfo", imsi);
				break;
			case 1:
				model.addAttribute("studioInfo", imsi);
				break;
			case 2:
				model.addAttribute("dressInfo", imsi);
				break;
			case 3:
				model.addAttribute("makeupInfo", imsi);
				break;
			}
		}
		
		int plan_no=budservice.getPlan(user_email).getPlan_no();
		int package_no = budservice.getPlan(user_email).getPackage_no();
		int discount =0;
		if(package_no==9999) {
			model.addAttribute("discount", discount);
			System.out.println("할인율: "+discount);
		}else {
			discount=budservice.getDiscount(package_no);
		model.addAttribute("discount", discount);
		}
		model.addAttribute("total_cost", total_cost*(100-discount)/100);
		PlanProgressVO planprogvo1 = budservice.getPlanProgress(plan_no);
		System.out.println("홀결제:"+planprogvo1.getPay_hall());
		planprogvo1.setPay_hall_ch(planprogvo1.getPay_hall() != 0);
		planprogvo1.setPay_stud_ch(planprogvo1.getPay_stud() != 0);
		planprogvo1.setPay_dres_ch(planprogvo1.getPay_dres() != 0);
		planprogvo1.setPay_make_ch(planprogvo1.getPay_make() != 0);
		
		planprogvo1.setContract_hall_ch("Y".equals(planprogvo1.getContract_hall()));
		planprogvo1.setContract_stud_ch("Y".equals(planprogvo1.getContract_stud()));
		planprogvo1.setContract_dres_ch("Y".equals(planprogvo1.getContract_dres()));
		planprogvo1.setContract_make_ch("Y".equals(planprogvo1.getContract_make()));
		
		model.addAttribute("planprog", planprogvo1);
		
		int contract_y_count = budservice.getContractProgess(plan_no);
		System.out.println(contract_y_count);
		model.addAttribute("contract_progress",(contract_y_count*100/4));
		
		int paid_total_amount = budservice.getPaidProgess(plan_no);
		System.out.println(paid_total_amount);
		model.addAttribute("paid_progress",(paid_total_amount*100/sumBudget));
		return "MyWeddingPlan";
		
	}
	
	@GetMapping("/saveprogress.do")
	public String goSave_progress(@ModelAttribute PlanProgressVO ppgvo, Model model) {
		String user_email = "user1@example.com";
		System.out.println("입력받은 내용:"+ppgvo.getPay_hall());
		ppgvo.setContract_hall(ppgvo.getContract_hall_ch() ? "Y" : "N");
		ppgvo.setContract_stud(ppgvo.getContract_stud_ch() ? "Y" : "N");
		ppgvo.setContract_dres(ppgvo.getContract_dres_ch() ? "Y" : "N");
		ppgvo.setContract_make(ppgvo.getContract_make_ch() ? "Y" : "N");	
		ppgvo.setPlan_no(budservice.getPlan(user_email).getPlan_no());
		budservice.setProgress(ppgvo);
		
		System.out.println(ppgvo.getContract_hall());
		System.out.println(ppgvo.getContract_hall_ch());
		
		System.out.println("저장중");
		System.out.println(ppgvo.getPay_hall());
		return "redirect:/mypage";
	}
	
	@PostMapping("/payment/success")
	public Map<String, Object> paymentSuccess(@RequestBody PaymentDTO payment) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	    	System.out.println("결제 정보 받음: " + payment);
	        // DB에 저장
	    	int plan_no=budservice.getPlan(payment.getUser_email()).getPlan_no();
	    	payment.setPlan_no(plan_no);
	    	payment.getCategorytoString();
	    	int aa =budservice.savePayment(payment); 
	    	
	    	if(aa>0) {
	        result.put("success", true);
	        System.out.println("결제 정보 저장완료: " + payment);
	    	}
	    	else 
	    		result.put("success", false);
	 	   
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("error", e.getMessage());
	    }
	    return result;
	}
	
}
