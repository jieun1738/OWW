package com.oww.oww1.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oww.oww1.mapper.LoanMapper;
import com.oww.oww1.vo.LoanVO;

@Controller
@RequestMapping("/admin/loan")
public class LoanController {

    private final LoanMapper loanMapper;

    public LoanController(LoanMapper loanMapper) {
        this.loanMapper = loanMapper;
    }

    
    @GetMapping("/list")
    public String list(Model model) {
        List<LoanVO> loans = loanMapper.findAll();
        model.addAttribute("loans", loans);
        return "admin/loan/loan-list"; 
    }

    // 승인 처리
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        loanMapper.approve(id);
        return "redirect:/fragments/loan/list"; 
    }

    // 거절 처리
    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        loanMapper.reject(id);
        return "redirect:/fragments/loan/list";
    }
}

