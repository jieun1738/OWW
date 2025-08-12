package com.example.demo.controller;

import com.example.demo.dto.ChartDto;
import com.example.demo.service.SafeBoxService;
import com.example.demo.service.SafeBoxService.GoalView;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/safe_box")
public class SafeBoxApiController {

    private final SafeBoxService service;

    public SafeBoxApiController(SafeBoxService service) {
        this.service = service;
    }

    @GetMapping("/goal")
    public GoalView goal(@RequestParam(defaultValue = "user1@example.com") String email) {
        return service.getGoal(email);
    }

    @GetMapping("/chart")
    public ChartDto chart(@RequestParam(defaultValue = "user1@example.com") String email) {
        return service.getCategoryChart(email);
    }
}
