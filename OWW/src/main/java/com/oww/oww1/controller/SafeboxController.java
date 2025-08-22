package com.oww.oww1.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oww.oww1.VO.GoalViewDTO;

@Controller
public class SafeboxController {

    // 루트로 들어오면 금고 화면으로 리다이렉트
	/*
	 * @GetMapping("/") public String home() { return "redirect:/mypage/safe_box"; }
	 */
    // 금고 화면
    @GetMapping("/mypage/safe_box")
    public String safeBox(Model model){
    	GoalViewDTO gv = new GoalViewDTO();
    	// 고정값 이므로 DB연결지점은 여기입니다.
    	gv.setTargetAmount(10_000_000L);
    	gv.setSavedAmount(7_000_000L);
    	gv.setRegular(true);
        model.addAttribute("goal", gv);
        return "safe/safe_box";
    }


	@GetMapping("/hello")
    public String hello(){ 
    	return "hello"; 
    	}



    
}
