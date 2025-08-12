package com.example.demo.controller;

import com.example.demo.service.SafeBoxService;
import com.example.demo.service.SafeBoxService.GoalView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SafeBoxPageController {

    private final SafeBoxService service;

    public SafeBoxPageController(SafeBoxService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/mypage/safe_box?email=user1@example.com";
    }

    /** 금고 페이지 */
    @GetMapping("/mypage/safe_box")
    public String safeBoxPage(@RequestParam(defaultValue = "user1@example.com") String email,
                              Model model) {

        GoalView goal = service.getGoal(email);
        model.addAttribute("goal", goal);

        // 사이드바/헤더 등에 쓰일 값 (없으면 빼도 됨)
        model.addAttribute("user", new UserView(email, "이수연"));

        int progress = (goal.targetAmount() > 0)
                ? (int)Math.round(Math.min(100, Math.max(0, goal.savedAmount() * 100.0 / goal.targetAmount())))
                : 0;
        model.addAttribute("progressPercent", progress);

        return "safe/safe_box"; // templates/safe/safe_box.html
    }

    public record UserView(String email, String name) {}
}
