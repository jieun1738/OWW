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

    /** 목록 조회 */
    @GetMapping
    public String list(@RequestParam(required = false) String q, Model model) {
        List<LoanVO> loans = loanService.findAll(q, 0, 0);
        model.addAttribute("loans", loans);
        return "admin/loans/list";
    }

    /** 상세 조회 */
    @GetMapping("/{loanNo}")
    public String detail(@PathVariable Long loanNo, Model model) {
        LoanVO loan = loanService.findById(loanNo);
        model.addAttribute("loan", loan);
        return "admin/loans/detail";
    }

    /** 등록 */
    @PostMapping
    public String insert(LoanVO vo) {
        loanService.insert(vo);
        return "redirect:/admin/loans";
    }

    /** 수정 */
    @PostMapping("/{loanNo}")
    public String update(@PathVariable Long loanNo, LoanVO vo) {
        vo.setLoanNo(loanNo);
        loanService.update(vo);
        return "redirect:/admin/loans/" + loanNo;
    }

    /** 삭제 */
    @PostMapping("/{loanNo}/delete")
    public String delete(@PathVariable Long loanNo) {
        loanService.delete(loanNo);
        return "redirect:/admin/loans";
    }
}
