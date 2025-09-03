package com.oww.oww1.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
		/* String email = resolveEmail(principal); */

        /* ===== 1) 목표/저축 조회 ===== */
		/*
		 * GoalDto goal = safeBoxService.getGoal(email); NumberFormat won =
		 * NumberFormat.getNumberInstance(Locale.KOREA);
		 */
        
        BigDecimal targetAmount = new BigDecimal("1000000");
      //Goal_id 에 따른 VO 받아와야함
        
        List<SafeBoxHistoryVO> historyList = new ArrayList<>();
        historyList.add(new SafeBoxHistoryVO(1, 1, new BigDecimal("10000"), LocalDate.of(2025, 8, 1), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(2, 1, new BigDecimal("15000"), LocalDate.of(2025, 8, 2), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(3, 1, new BigDecimal("12000"), LocalDate.of(2025, 8, 3), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(4, 1, new BigDecimal("18000"), LocalDate.of(2025, 8, 4), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(5, 1, new BigDecimal("13000"), LocalDate.of(2025, 8, 5), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(6, 1, new BigDecimal("16000"), LocalDate.of(2025, 8, 6), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(7, 1, new BigDecimal("20000"), LocalDate.of(2025, 8, 7), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(8, 1, new BigDecimal("17000"), LocalDate.of(2025, 8, 8), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(9, 1, new BigDecimal("19000"), LocalDate.of(2025, 8, 9), "user2@example.com"));
        historyList.add(new SafeBoxHistoryVO(10, 1, new BigDecimal("21000"), LocalDate.of(2025, 8, 10), "user2@example.com"));
        
        BigDecimal savedAmount = new BigDecimal("0");
        for(SafeBoxHistoryVO imsi: historyList) {
        	savedAmount.add(imsi.getAmount());
        }
        
        double progress_double = savedAmount.divide(targetAmount, 4, RoundingMode.HALF_UP).doubleValue();
        int progress = (int)(progress_double*100);
        
        model.addAttribute("targetAmount", targetAmount);
        model.addAttribute("historyList", historyList);
        model.addAttribute("savedAmount", savedAmount);
        model.addAttribute("monthly", 20000);
        model.addAttribute("progress", progress);
        
        /* ===== 2) 사이드바 예산 조회 ===== */
		/*
		 * BudgetForm budget = budgetService.getBudget(email); long total = 0L; if
		 * (budget != null) { total = safeSum(budget.getWeddingHall()) +
		 * safeSum(budget.getStudio()) + safeSum(budget.getDress()) +
		 * safeSum(budget.getMakeup()); }
		 */
        

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
		
		/*
		 * model.addAttribute("user", email); model.addAttribute("budget", budget);
		 * model.addAttribute("total", total); model.addAttribute("totalFmt",
		 * won.format(total) + "원");
		 */
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

