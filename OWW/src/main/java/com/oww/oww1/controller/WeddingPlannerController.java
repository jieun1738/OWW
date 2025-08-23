package com.oww.oww1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.service.PlannerService;

import lombok.RequiredArgsConstructor;

/**
 * 웨딩플래너 컨트롤러 (가장 기본적인 링크/폼 흐름)
 * - 마이페이지 코드는 절대 수정하지 않음
 * - 스키마(product/package/plan) 및 요구사항을 그대로 준수
 */
@Controller
@RequestMapping("/planner")
@RequiredArgsConstructor
public class WeddingPlannerController {

    private final PlannerService plannerService;

    /** 진입/요약 */
    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingPlannerInfo";
    }

    /** 소개 */
    @GetMapping("/intro")
    public String intro(Model model){
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingPlannerIntro";
    }

    /** 패키지 목록 (원가/할인가 강조 표시) */
    @GetMapping("/category")
    public String category(Model model){
        model.addAttribute("packages", plannerService.listPackages());
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingCategory";
    }

    /** DIY: 카테고리별 상품 목록 (0: hall, 1: studio, 2: dress, 3: makeup) */
    @GetMapping("/diy")
    public String diy(@RequestParam(defaultValue = "0") int cat, Model model){
        model.addAttribute("cat", cat);
        model.addAttribute("products", plannerService.findProductsByCategory(cat));
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingDIY";
    }

    /**
     * 확인 화면
     * - 패키지: packageNo만 전달
     * - DIY   : hall/studio/dress/makeup 4개 번호 전달
     */
    @PostMapping("/confirm")
    public String confirm(
            @RequestParam(required = false) Integer packageNo,
            @RequestParam(required = false) Integer hall,
            @RequestParam(required = false) Integer studio,
            @RequestParam(required = false) Integer dress,
            @RequestParam(required = false) Integer makeup,
            Model model){

        boolean isPackage = (packageNo != null);
        int total = 0, discountPercent = 0, finalTotal = 0;

        if (isPackage){
            PackageVO p = plannerService.getPackage(packageNo);
            total = p.getTotal_price();
            discountPercent = p.getDiscount();
            finalTotal = p.getFinal_price();
            model.addAttribute("pkg", p);
        }

        model.addAttribute("isPackage", isPackage);
        model.addAttribute("packageNo", packageNo);
        model.addAttribute("hall", hall);
        model.addAttribute("studio", studio);
        model.addAttribute("dress", dress);
        model.addAttribute("makeup", makeup);

        model.addAttribute("total", total);
        model.addAttribute("discountPercent", discountPercent);
        model.addAttribute("finalTotal", finalTotal);
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingConfirm";
    }

    /** 최종 확정 → plan INSERT (DIY는 package_no=9999 규칙) */
    @PostMapping("/plan/confirm")
    public String planConfirm(
            @RequestParam String userEmail,
            @RequestParam(required = false) Integer packageNo,
            @RequestParam int hall,
            @RequestParam int studio,
            @RequestParam int dress,
            @RequestParam int makeup){

        plannerService.confirmPlan(userEmail, packageNo, hall, studio, dress, makeup);
        return "redirect:/planner/plan/list?userEmail=" + userEmail;
    }

    /** 확정 플랜 목록 */
    @GetMapping("/plan/list")
    public String planList(@RequestParam String userEmail, Model model){
        model.addAttribute("plans", plannerService.getPlansByUser(userEmail));
        model.addAttribute("progressPercent", 0);
        return "WeddingPlanner/WeddingPlanList";
    }
}
