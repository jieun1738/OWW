package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminHomeController {

    // 대시보드
    @GetMapping({"/", "/admin"})
    public String dashboard(Model model) {
        model.addAttribute("active", "dashboard");
        return "admin-dashboard";
    }

    // 상품관리
    @GetMapping("/admin/product")
    public String product(Model model) {
        model.addAttribute("active", "product");
        return "admin-product";  // templates/admin-product.html
    }

    // 대출승인
    @GetMapping("/admin/loan")
    public String loan(Model model) {
        model.addAttribute("active", "loan");
        return "admin-loan";     // templates/admin-loan.html
    }
}
