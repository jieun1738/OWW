// src/main/java/com/example/demo/controller/AdminLoanController.java
package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.LoanService;
import com.example.demo.vo.LoanVO;

@Controller
@RequestMapping("/admin/loans")
public class AdminLoanController {

    private final LoanService loanService;

    public AdminLoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        List<LoanVO> loans = loanService.findAll(q, 0, 0);
        model.addAttribute("loans", loans);
        return "admin/loans/list";   // 관리자용 뷰
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        LoanVO loan = loanService.findById(id);
        model.addAttribute("loan", loan);
        return "admin/loans/detail";
    }

    @PostMapping
    public String insert(LoanVO vo) {
        loanService.insert(vo);
        return "redirect:/admin/loans";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, LoanVO vo) {
        vo.setLoanNo(id);
        loanService.update(vo);
        return "redirect:/admin/loans/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        loanService.delete(id);
        return "redirect:/admin/loans";
    }
}
