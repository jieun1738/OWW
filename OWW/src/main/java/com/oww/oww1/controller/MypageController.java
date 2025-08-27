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
import org.springframework.web.bind.annotation.ResponseBody;

import com.oww.oww1.OwwApplication;
import com.oww.oww1.VO.PaymentDTO;
import com.oww.oww1.VO.PlanProgressVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.service.DashboardService;


@Controller
public class MypageController {

    private final OwwApplication owwApplication;
    
	@Autowired
	DashboardService dashservice;
	

    MypageController(OwwApplication owwApplication) {
        this.owwApplication = owwApplication;
    }

    @GetMapping("/budgetSetting")
    public String goBudgetSetting(Model model) {
    	String user_email = "user2@example.com";
		model.addAttribute("totalBudget", dashservice.getBudget(user_email));
		int sumBudget=dashservice.sumBudget(user_email);
		model.addAttribute("sumBudget", sumBudget);	
		int plan_no=dashservice.getPlan(user_email).getPlan_no();
		int contract_y_count = dashservice.getContractProgess(plan_no);
		System.out.println(contract_y_count);
		model.addAttribute("contract_progress",(contract_y_count*100/4));
    	return "Budget";
    }
    
    
    
	@GetMapping("/mypage")
	public String goMypage(Model model) {
		String user_email = "user2@example.com";
		model.addAttribute("totalBudget", dashservice.getBudget(user_email));
		int sumBudget=dashservice.sumBudget(user_email);
		model.addAttribute("sumBudget", sumBudget);	
		List<ProductVO> user_product = dashservice.getProductInfo(user_email);
		
		System.out.println(user_product);
		
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
		System.out.println(dashservice.getPlan(user_email));
		int plan_no=dashservice.getPlan(user_email).getPlan_no();
		System.out.println("plan_no: "+plan_no);
		int package_no = dashservice.getPlan(user_email).getPackage_no();
		System.out.println("▶ packageNo 전달값: " + package_no);
		int discount =0;
		if(package_no==9999) {
			model.addAttribute("discount", discount);
			System.out.println("할인율: "+discount);
		}else {
			discount=dashservice.getDiscount(package_no);
			System.out.println("▶ Mapper 반환값: " + discount);
		model.addAttribute("discount", discount);
		}
		model.addAttribute("total_cost", total_cost*(100-discount)/100);
		PlanProgressVO planprogvo1 = dashservice.getPlanProgress(plan_no);
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
		
		int contract_y_count = dashservice.getContractProgess(plan_no);
		System.out.println(contract_y_count);
		model.addAttribute("contract_progress",(contract_y_count*100/4));
		
		int paid_total_amount = dashservice.getPaidProgess(plan_no);
		System.out.println(paid_total_amount);
		model.addAttribute("paid_progress",(paid_total_amount*100/sumBudget));
		return "MyWeddingPlan";
		
	}
	
	@PostMapping("payment/success")
	public String paymentSuccess(@RequestBody PaymentDTO payment) {
	    Map<String, Object> result = new HashMap<>();
	    try {
	    	System.out.println("결제 정보 받음: " + payment);
	        // DB에 저장
	    	int plan_no=dashservice.getPlan(payment.getUser_email()).getPlan_no();
	    	payment.setPlan_no(plan_no);
	    	int category=payment.getCategory();
	    	double real_pay_ratio = (100-payment.getDiscount())/100.0;
	    	if(category==4) {
	    		List<ProductVO> all_productInfo = dashservice.getProductInfo(payment.getUser_email());
	    		for(ProductVO imsi:all_productInfo) {
	    			
	    				payment.setCategory(imsi.getCategory());
	    				payment.setAmount((int)(imsi.getCost()*real_pay_ratio));
	    				payment.setItemName(imsi.getProduct_name());
	    				payment.getCategorytoString();
	    				int aa_in =dashservice.savePayment(payment); 
	    		    		if(aa_in>0) {
	    		    			result.put("success", true);
	    		    			System.out.println(payment.getPay_category_str()+" 결제 정보 저장완료: " + payment);
	    		    		}
	    		    		else 
	    		    			result.put("success", false);
	    			
	    		}
	    	
	    	}else {
	    	payment.getCategorytoString();
	    	int aa =dashservice.savePayment(payment); 
	    	if(aa>0) {
	        result.put("success", true);
	        System.out.println("결제 정보 저장완료: " + payment);
	    	}
	    	else 
	    		result.put("success", false);
	    	}
	    	
	    } catch (Exception e) {
	        result.put("success", false);
	        result.put("error", e.getMessage());
	    }
	    
	    return "redirect:/mypage";
	}
	
	
	
	@PostMapping("/mypage/updateContract")
	@ResponseBody
	public Map<String, Object> updateContract(@RequestBody Map<String, Object> param){
	    Map<String, Object> result = new HashMap<>();
	    try {
	    		String user_email = "user2@example.com";
	    		int plan_no=dashservice.getPlan(user_email).getPlan_no();
	        String contract_category = (String)param.get("baseName");
	        boolean checked = (boolean) param.get("checked");
	        String YorN = checked?"Y":"N";
	        int updated = dashservice.updateContract(contract_category, YorN, plan_no); // DB 저장
	       
	     // progress 계산
	        int contract_y_count = dashservice.getContractProgess(plan_no);
			System.out.println(contract_y_count);
			int contract_progress_width = contract_y_count*100/4;
	        

	        result.put("success", true);
	        result.put("progress", contract_progress_width);
	    } catch(Exception e) {
	        result.put("success", false);
	        result.put("error", e.getMessage());
	    }
	    return result;
	}

	
}
