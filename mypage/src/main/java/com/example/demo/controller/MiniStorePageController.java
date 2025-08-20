package com.example.demo.controller;

import com.example.demo.service.MiniStoreService.PurchaseView;
import com.example.demo.service.MiniStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MiniStorePageController {

    private final MiniStoreService service;

    public MiniStorePageController(MiniStoreService service) {
        this.service = service;
    }

    @GetMapping("/mypage/ministore/payments")
    public String payments(
            @RequestParam(defaultValue = "user1@example.com") String email,
            Model model) {

        List<PurchaseView> list = service.getPayments(email);
        model.addAttribute("payments", list);

        // 사이드바에서 '미니스토어 결제내역' 활성화
        model.addAttribute("active", "ministore");
        model.addAttribute("user", new UserView(email, "이수연"));
        model.addAttribute("progressPercent", 70); // 필요 시

        return "ministore/payments"; // templates/ministore/payments.html
    }

    public record UserView(String email, String name) {}
}
