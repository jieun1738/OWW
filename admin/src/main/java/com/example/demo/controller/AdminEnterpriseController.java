package com.example.demo.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.EnterpriseService;
import com.example.demo.vo.EnterpriseVO;

@Controller
@RequestMapping("/admin/enterprise")
public class AdminEnterpriseController {

    private final EnterpriseService service;

    public AdminEnterpriseController(EnterpriseService service) {
        this.service = service;
    }

    /** 업체 목록 페이지 */
    @GetMapping({"", "/", "/list"})
    public String list(Model model) {
        model.addAttribute("active", "enterprise");
        model.addAttribute("items", service.findAll());
        return "fragments/enterprise-list";   // templates/fragments/enterprise-list.html
    }

    /** 업체 등록 페이지 */
    @GetMapping("/new")
    public String addForm(Model model) {
        model.addAttribute("active", "enterprise");
        model.addAttribute("form", new EnterpriseVO());
        return "fragments/enterprise-form";   // templates/fragments/enterprise-form.html
    }

    /** 업체 등록 처리 */
    @PostMapping("/new")
    public String addSubmit(EnterpriseVO form,
                            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        service.saveOrUpdate(form, file);
        return "redirect:/admin/enterprise";
    }

    /** 업체 수정 페이지 */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("active", "enterprise");
        model.addAttribute("form", service.findById(id));
        return "fragments/enterprise-form";
    }

    @PostMapping("/{id}/edit")
    public String editSubmit(@PathVariable Long id,
                             EnterpriseVO form,
                             @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        form.setId(id);
        service.saveOrUpdate(form, file);
        return "redirect:/admin/enterprise";
    }

    /** 업체 삭제 처리 */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/admin/enterprise";
    }
}
