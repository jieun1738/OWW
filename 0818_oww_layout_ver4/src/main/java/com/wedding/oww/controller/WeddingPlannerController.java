package com.wedding.oww.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeddingPlannerController {
	
	
	// 테스트 페이지 목록
	@GetMapping("/")
    public String Mainpage() {
        return "index";
    }
	
	@GetMapping("/WeddingPlanner/WeddingPlannerIntro")
    public String weddingPlannerIntro(Model model) {
        // 사이드바 활성화 메뉴명 (프래그먼트 하이라이트에 사용)
        model.addAttribute("active", "WeddingPlannerIntro");

        // 진행률 또는 사용자 정보가 필요한 경우 추가
        model.addAttribute("progressPercent", 0); // 초기값
        model.addAttribute("user", "guest");      // 세션 사용자명 전달 가능

        // 추가 정보 (필요 시 DB/MyBatis 연동)
        model.addAttribute("lastUpdated", LocalDate.now());

        return "WeddingPlanner/WeddingPlannerIntro";
    }

    @GetMapping("/WeddingPlanner/WeddingPlannerInfo")
    public String weddingPlannerInfo(Model model) {
        // 사이드바 하이라이트 및 진행률 등 기존 프래그먼트 파라미터
        model.addAttribute("active", "WeddingPlannerInfo");
        model.addAttribute("progressPercent", 0); // 필요 시 동적 반영
        model.addAttribute("user", "guest");      // 필요 시 세션 사용자명 전달

        // 화면 요약 데이터(추후 MyBatis 연동 시 실제 값으로 대체)
        model.addAttribute("packageCount", 0);
        model.addAttribute("diyCount", 0);
        model.addAttribute("savedPlanCount", 0);
        model.addAttribute("lastUpdated", LocalDate.now());

        return "WeddingPlanner/WeddingPlannerInfo";
    }
    
    @GetMapping("/WeddingPlanner/WeddingThemePackages")
    public String weddingThemePackages(Model model) {
        model.addAttribute("active", "WeddingThemePackages"); // 사이드바 활성화 키
        model.addAttribute("progressPercent", 0);
        model.addAttribute("user", "guest");
        model.addAttribute("packageCount", 0);
        return "WeddingPlanner/WeddingThemePackages";
    }
}
