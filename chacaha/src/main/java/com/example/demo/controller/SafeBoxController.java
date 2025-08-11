package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SafeBoxController {

    // 루트로 들어오면 금고 화면으로 리다이렉트
    @GetMapping("/")
    public String home() {
        return "redirect:/mypage/safe_box";
    }

    // 금고 화면
    @GetMapping("/mypage/safe_box")
    public String safeBox(Model model){
        model.addAttribute("goal", new GoalView(10_000_000L, 7_000_000L, true));
        return "safe/safe_box";
    }


    
    @GetMapping("/hello")
    public String hello(){ return "hello"; }


    // 화면 바인딩용 간단 DTO
    public static class GoalView {
        public long targetAmount;
        public long savedAmount;
        public boolean regular;

        public GoalView(long targetAmount, long savedAmount, boolean regular) {
            this.targetAmount = targetAmount;
            this.savedAmount = savedAmount;
            this.regular = regular;
        }
    }
}
