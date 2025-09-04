package com.oww.oww1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oww.oww1.service.Plannerservice;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class WeddingPlannerController {

    private final Plannerservice service;

    /** 테마별 패키지 목록 */
    @GetMapping("/planner/category")
    public String category(Model model) {
        model.addAttribute("package_cards", service.get_category_cards());
        return "WeddingPlanner/WeddingCategory";
    }
}
