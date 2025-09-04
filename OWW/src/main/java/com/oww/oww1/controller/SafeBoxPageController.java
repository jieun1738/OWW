package com.oww.oww1.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oww.oww1.VO.BudgetVO;
import com.oww.oww1.VO.PlanProgressVO;
import com.oww.oww1.VO.SafeBoxHistoryVO;
import com.oww.oww1.service.BudgetService;
import com.oww.oww1.service.DashboardService;
import com.oww.oww1.service.SafeBoxService;

@Controller
public class SafeBoxPageController {

    private final SafeBoxService safeBoxService;
    private final BudgetService budgetService;

    private static final String FALLBACK_EMAIL = "user1@example.com";

    public SafeBoxPageController(SafeBoxService safeBoxService,
                                 BudgetService budgetService) {
        this.safeBoxService = safeBoxService;
        this.budgetService  = budgetService;
    }

    @GetMapping({"", "/"})
    public String mypageRoot() {
		return "redirect:/mypage";/* /safe_box */
    }
    
	@Autowired
	DashboardService dashservice;
	

	
    @GetMapping({"/safe_box", "/safe_box/", "/safe-box", "/safe-box/"})
    public String safeBoxPage(Principal principal, Model model) {
        
        BigDecimal targetAmount = new BigDecimal("1000000");
      //Goal_id 에 따른 VO 받아와야함
        
        List<SafeBoxHistoryVO> historyList = new ArrayList<>();
        //7월 더미
        historyList.add(new SafeBoxHistoryVO(11, 1, new BigDecimal("9000"),  LocalDate.of(2025, 7, 1), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(12, 1, new BigDecimal("11000"), LocalDate.of(2025, 7, 2), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(13, 1, new BigDecimal("8000"),  LocalDate.of(2025, 7, 4), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(14, 1, new BigDecimal("15000"), LocalDate.of(2025, 7, 6), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(15, 1, new BigDecimal("12000"), LocalDate.of(2025, 7, 7), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(16, 1, new BigDecimal("13000"), LocalDate.of(2025, 7, 9), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(17, 1, new BigDecimal("14000"), LocalDate.of(2025, 7, 11), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(18, 1, new BigDecimal("16000"), LocalDate.of(2025, 7, 12), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(19, 1, new BigDecimal("17000"), LocalDate.of(2025, 7, 18), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(20, 1, new BigDecimal("18000"), LocalDate.of(2025, 7, 22), "user2@example.com"));
        //9월 더미
        historyList.add(new SafeBoxHistoryVO(21, 1, new BigDecimal("9000"),  LocalDate.of(2025, 9, 1), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(22, 1, new BigDecimal("11000"), LocalDate.of(2025, 9, 2), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(23, 1, new BigDecimal("8000"),  LocalDate.of(2025, 9, 4), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(24, 1, new BigDecimal("15000"), LocalDate.of(2025, 9, 6), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(25, 1, new BigDecimal("12000"), LocalDate.of(2025, 9, 7), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(26, 1, new BigDecimal("13000"), LocalDate.of(2025, 9, 9), "user2@example.com"));
        //8월 더미
        historyList.add(new SafeBoxHistoryVO(1, 1, new BigDecimal("10000"), LocalDate.of(2025, 8, 2), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(2, 1, new BigDecimal("15000"), LocalDate.of(2025, 8, 5), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(3, 1, new BigDecimal("12000"), LocalDate.of(2025, 8, 6), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(4, 1, new BigDecimal("18000"), LocalDate.of(2025, 8, 9), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(5, 1, new BigDecimal("13000"), LocalDate.of(2025, 8, 11), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(6, 1, new BigDecimal("16000"), LocalDate.of(2025, 8, 13), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(7, 1, new BigDecimal("20000"), LocalDate.of(2025, 8, 16), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(8, 1, new BigDecimal("17000"), LocalDate.of(2025, 8, 25), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(9, 1, new BigDecimal("19000"), LocalDate.of(2025, 8, 26), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(10, 1, new BigDecimal("21000"), LocalDate.of(2025, 8, 30), "user2@example.com"));
        
        BigDecimal savedAmount = new BigDecimal("0");
        int savedAmount_progress = 0;
        for(SafeBoxHistoryVO imsi: historyList) {
            savedAmount = savedAmount.add(imsi.getAmount());
            
            if(imsi.getAmount().compareTo(new BigDecimal("10000")) >= 0) 
            	savedAmount_progress += 1; 
        }
        
        int save_progress = (int)(((double) savedAmount_progress / (double) historyList.size())*100);
        
        
        System.out.println("저금액"+savedAmount);
        double progress_double = savedAmount.divide(targetAmount, 4, RoundingMode.HALF_UP).doubleValue();
        int progress = (int)(progress_double*100);
        
        List<Map<String, Object>> jsHistoryList = historyList.stream()
        	    .<Map<String,Object>>map(h -> {
        	        Map<String,Object> m = new HashMap<>();
        	        m.put("amount", h.getAmount().intValue());
        	        m.put("payment_date", h.getPayment_date().toString());
        	        return m;
        	    })
        	    .collect(Collectors.toList());
        model.addAttribute("targetAmount", targetAmount);
        model.addAttribute("historyList", jsHistoryList);
        model.addAttribute("savedAmount", savedAmount);
        model.addAttribute("monthly", 20000);
        model.addAttribute("progress", progress);
        model.addAttribute("save_progress", save_progress);
        

		String user_email = "user2@example.com";
		int plan_no = dashservice.getPlan(user_email).getPlan_no();
		BudgetVO budgetVO = dashservice.getBudget(user_email);
		PlanProgressVO planprogress = dashservice.getPlanProgress(plan_no);

		// 사이드바 진행율용
		int sumBudget = budgetVO.getAmount();
		model.addAttribute("sumBudget", sumBudget);
		model.addAttribute("totalBudget", dashservice.getBudget(user_email));

		int contract_y_count = dashservice.getContractProgess(plan_no);
		model.addAttribute("contract_progress", (contract_y_count * 100 / 4));
		
        return "Mypage/safe_box";
    }

    private String resolveEmail(Principal principal) {
        return (principal != null && principal.getName() != null && !principal.getName().isBlank())
                ? principal.getName()
                : FALLBACK_EMAIL;
    }

    private long safeSum(Long v) { return (v != null) ? v : 0L; }
    private long safeSum(long v)  { return v; }
}

