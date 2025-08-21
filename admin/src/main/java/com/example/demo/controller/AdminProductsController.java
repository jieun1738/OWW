package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.mapper.ProductMapper;
import com.example.demo.vo.ProductVO;

@Controller
@RequestMapping("/admin/products")
public class AdminProductsController {

    private final ProductMapper productMapper;

    public AdminProductsController(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /** 리스트 + 검색(q: 업체/상품명) + 카테고리 필터 + 페이징 */
    @GetMapping
    public String list(@RequestParam(defaultValue = "") String q,
                       @RequestParam(required = false) Integer category,
                       @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "20") int size,
                       Model model) {

        int offset = Math.max(0, (page - 1) * size);
        int total = productMapper.count(q, category);
        List<ProductVO> rows = productMapper.findAll(q, category, offset, size);

        model.addAttribute("rows", rows);
        model.addAttribute("total", total);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("q", q);
        model.addAttribute("category", category);
        return "admin-products"; // 목록 템플릿
    }

 // 등록 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("dto", new ProductVO());
        model.addAttribute("mode", "create");
        return "admin-product-form";
    }

    /** 등록 처리 */
    @PostMapping
    public String create(@ModelAttribute ProductVO dto) {
        // product_no가 트리거 자동채번이면 dto.setProductNo(null);
        productMapper.insert(dto);
        return "redirect:/admin/products";
    }

 // 수정 폼
    @GetMapping("/{id}")
    public String editForm(@PathVariable int id, Model model) {
        model.addAttribute("dto", productMapper.findById(id));
        model.addAttribute("mode", "edit");
        return "admin-product-form";
    }

    /** 수정 처리 */
    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute ProductVO dto) {
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
