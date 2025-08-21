// AdminPackageController.java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.mapper.PackageMapper;
import com.example.demo.vo.PackageVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/packages")
@RequiredArgsConstructor
public class AdminPackageController {

    private final PackageMapper packageMapper;

    @GetMapping
    public String list(Model model){
        model.addAttribute("rows", packageMapper.findAll());
        return "admin-packages"; // 목록 템플릿
    }

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("dto", new PackageVO());
        // 드롭다운 데이터
        model.addAttribute("halls",   packageMapper.findProductsByCategory(0));
        model.addAttribute("studios", packageMapper.findProductsByCategory(1));
        model.addAttribute("dresses", packageMapper.findProductsByCategory(2));
        model.addAttribute("makeups", packageMapper.findProductsByCategory(3));
        return "admin-package-form";
    }

    @PostMapping
    public String create(@ModelAttribute PackageVO dto){
        packageMapper.insert(dto);
        return "redirect:/admin/packages";
    }

    @GetMapping("/{id}")
    public String editForm(@PathVariable int id, Model model){
        model.addAttribute("dto", packageMapper.findById(id));
        model.addAttribute("halls",   packageMapper.findProductsByCategory(0));
        model.addAttribute("studios", packageMapper.findProductsByCategory(1));
        model.addAttribute("dresses", packageMapper.findProductsByCategory(2));
        model.addAttribute("makeups", packageMapper.findProductsByCategory(3));
        return "admin-package-form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable int id, @ModelAttribute PackageVO dto){
        dto.setPackageNo(id);
        packageMapper.update(dto);
        return "redirect:/admin/packages";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable int id){
        packageMapper.delete(id);
        return "redirect:/admin/packages";
    }
}
