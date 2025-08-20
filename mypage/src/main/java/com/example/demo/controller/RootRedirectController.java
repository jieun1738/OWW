// RootRedirectController.java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootRedirectController {
    @GetMapping("/")
    public String home() {
        // 편한 기본 이메일 붙여서 금고 페이지로
        return "redirect:/mypage/safe_box?email=user1@example.com";
    }
}
