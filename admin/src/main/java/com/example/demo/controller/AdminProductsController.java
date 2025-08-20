package com.example.demo.controller;


import com.example.demo.mapper.ProductMapper;
import com.example.demo.vo.ProductDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    private final ProductMapper productMapper;

    public AdminProductsController(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /** 리스트 + 검색 + 페이징 */
    @GetMapping
    public String list(@RequestParam(defaultValue = "") String q,
                       @RequestParam(required = false) Integer category,
                       @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "20") int size,
                       Model model) {

        int offset = Math.max(0, (page - 1) * size);
        int total = productMapper.count(q, category);
        List<ProductDto> rows = productMapper.findAll(q, category, offset, size);

        model.addAttribute("rows", rows);
        model.addAttribute("total", total);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("q", q);
        model.addAttribute("category", category);
        model.addAttribute("active", "products"); 

        return "admin-products"; // 목록 템플릿
    }

    /** 등록 폼 */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("dto", new ProductDto());
        model.addAttribute("mode", "create");
        return "admin-product-form";
    }

    /** 등록 처리 */
    @PostMapping
    public String create(@ModelAttribute ProductDto dto) {
        productMapper.insert(dto);
        return "redirect:/admin/products";
    }

    /** 수정 폼 */
    @GetMapping("/{id}")
    public String editForm(@PathVariable int id, Model model) {
        ProductDto dto = productMapper.findById(id);
        model.addAttribute("dto", dto);
        model.addAttribute("mode", "edit");
        return "admin-product-form";
    }

    /** 수정 처리 */
    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute ProductDto dto) {
        dto.setProductNo(id);
        productMapper.update(dto);
        return "redirect:/admin/products";
    }

    /** 삭제 */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id) {
        productMapper.delete(id);
        return "redirect:/admin/products";
    }
}
