package com.oww.oww1.controller;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oww.oww1.VO.BudgetForm;
import com.oww.oww1.VO.GoalDto;
import com.oww.oww1.service.BudgetService;
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
        return "redirect:/mypage/safe_box";
    }

    @GetMapping({"/safe_box", "/safe_box/", "/safe-box", "/safe-box/"})
    public String safeBoxPage(Principal principal, Model model) {
        String email = resolveEmail(principal);

        /* ===== 1) 목표/저축 조회 ===== */
        GoalDto goal = safeBoxService.getGoal(email);
        NumberFormat won = NumberFormat.getNumberInstance(Locale.KOREA);

        model.addAttribute("goal", goal);
        model.addAttribute("targetFmt", won.format(goal.targetAmount()) + "원");
        model.addAttribute("savedFmt",  won.format(goal.savedAmount())  + "원");
        model.addAttribute("remainFmt", won.format(goal.remaining())    + "원");
        model.addAttribute("progressPercent", goal.percent());

        /* ===== 2) 사이드바 예산 조회 ===== */
        BudgetForm budget = budgetService.getBudget(email);
        long total = 0L;
        if (budget != null) {
            total = safeSum(budget.getWeddingHall()) +
                    safeSum(budget.getStudio()) +
                    safeSum(budget.getDress()) +
                    safeSum(budget.getMakeup());
        }

        model.addAttribute("user", email);
        model.addAttribute("budget", budget);
        model.addAttribute("total", total);
        model.addAttribute("totalFmt", won.format(total) + "원");

        return "safe/safe_box";
    }

    private String resolveEmail(Principal principal) {
        return (principal != null && principal.getName() != null && !principal.getName().isBlank())
                ? principal.getName()
                : FALLBACK_EMAIL;
    }

    private long safeSum(Long v) { return (v != null) ? v : 0L; }
    private long safeSum(long v)  { return v; }
}

