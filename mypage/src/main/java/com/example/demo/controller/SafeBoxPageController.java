// SafeBoxPageController.java
package com.example.demo.controller;

import com.example.demo.service.SafeBoxService;
import com.example.demo.service.SafeBoxService.GoalView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/mypage")
public class SafeBoxPageController {

    private final SafeBoxService service;
    public SafeBoxPageController(SafeBoxService service){ this.service = service; }

    // /mypage/safe와 /mypage/safe_box 둘 다 진입 가능하게
    @GetMapping({"/safe_box","/safe"})
    public String safeBoxPage(@RequestParam(defaultValue="user1@example.com") String email,
                              Model model){
        GoalView goal = service.getGoal(email);
        model.addAttribute("goal", goal);
        model.addAttribute("user", new UserView(email, "이수연"));
        int progress = (goal.targetAmount() > 0)
                ? (int)Math.round(Math.min(100, Math.max(0, goal.savedAmount() * 100.0 / goal.targetAmount())))
                : 0;
        model.addAttribute("progressPercent", progress);
        model.addAttribute("active", "safe");
        return "safe/safe_box";
    }

    public record UserView(String email, String name) {}
}
