package com.oww.oww1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oww.oww1.service.EnterpriseService;
import com.oww.oww1.service.ProductService;
import com.oww.oww1.vo.ProductVO;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductService productService;
    private final EnterpriseService enterpriseService;

    public ProductController(ProductService productService, EnterpriseService enterpriseService) {
        this.productService = productService;
        this.enterpriseService = enterpriseService;
    }

    // 📌 상품 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin-product";  // templates/admin-product.html
    }

    // 📌 상품 등록 폼
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("productForm", new ProductVO());
        model.addAttribute("enterprises", enterpriseService.findAll());
        return "fragments/admin-product-new"; // templates/fragments/admin-product-new.html
    }

    //  상품 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        ProductVO product = productService.findById(id);
        model.addAttribute("productForm", product);  
        model.addAttribute("enterprises", enterpriseService.findAll());
        return "fragments/admin-product-edit"; //  templates/fragments/admin-product-edit.html
    }

    // 상품 저장 (등록 + 수정)
    @PostMapping
    public String save(@ModelAttribute("productForm") ProductVO form) {
        productService.save(form);
        return "redirect:/admin/products"; 
    }

    //  상품 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }

    
}