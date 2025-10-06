// src/main/java/com/example/demo/controller/AdminPackageController.java
package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.PackageService;
import com.example.demo.vo.PackageVO;
import com.example.demo.vo.ProductVO;

@Controller
@RequestMapping("/admin/packages")
public class AdminPackageController {

    private final PackageService packageService;

    public AdminPackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q,
                       @RequestParam(required = false) Integer type,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       Model model) {
        model.addAttribute("packages", packageService.findAll(q, type, page, size));
        model.addAttribute("total", packageService.count(q, type));
        model.addAttribute("q", q); model.addAttribute("type", type);
        model.addAttribute("page", page); model.addAttribute("size", size);
        return "admin/packages/list";
    }

    @GetMapping("/{packageNo}")
    public String detail(@PathVariable Long packageNo, Model model) {
        model.addAttribute("pkg", packageService.findByPackageNo(packageNo));
        return "admin/packages/detail";
    }

    @PostMapping
    public String insert(PackageVO vo) {
        packageService.insert(vo);
        return "redirect:/admin/packages";
    }

    @PostMapping("/{packageNo}")
    public String update(@PathVariable Long packageNo, PackageVO vo) {
        vo.setPackageNo(packageNo);
        packageService.update(vo);
        return "redirect:/admin/packages/" + packageNo;
    }

    @PostMapping("/{packageNo}/discount")
    public String updateDiscount(@PathVariable Long packageNo, @RequestParam int discount) {
        packageService.updateDiscount(packageNo, discount);
        return "redirect:/admin/packages/" + packageNo;
    }

    @PostMapping("/{packageNo}/delete")
    public String delete(@PathVariable Long packageNo) {
        packageService.delete(packageNo);
        return "redirect:/admin/packages";
    }

    // 드롭다운 (AJAX) — product.category: varchar(20)
    @GetMapping("/products")
    @ResponseBody
    public List<ProductVO> products(@RequestParam String category) {
        return packageService.findProductsByCategory(category);
    }
}
