package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.mapper.PackageMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.vo.PackageVO;
import com.example.demo.vo.ProductVO;

@Controller
@RequestMapping("/admin/packages")
public class PackageController {

    private final PackageMapper packageMapper;
    private final ProductMapper productMapper;

    public PackageController(PackageMapper packageMapper, ProductMapper productMapper) {
        this.packageMapper = packageMapper;
        this.productMapper = productMapper;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("packages", packageMapper.findAll());
        return "fragments/package-list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        List<ProductVO> products = productMapper.findAll();
        model.addAttribute("products", products);
        model.addAttribute("form", new PackageVO());
        return "fragments/package-form";
    }

    @PostMapping
    public String save(@ModelAttribute PackageVO form) {
        if (form.getId() == null) {
            packageMapper.insert(form);
        } else {
            packageMapper.update(form);
        }
        return "redirect:/fragments/packages";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("form", packageMapper.findById(id));
        model.addAttribute("products", productMapper.findAll());
        return "admin/package/package-form";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        packageMapper.delete(id);
        return "redirect:/fragments/packages";
    }
}
