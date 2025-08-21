package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.ProductMapper;
import com.example.demo.vo.ProductVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    public List<ProductVO> getList(String q, Integer category, int page, int size) {
        int offset = Math.max(0, (page - 1) * size);
        return productMapper.findAll(q, category, offset, size);
    }

    public int getTotal(String q, Integer category) {
        return productMapper.count(q, category);
    }

    public void save(ProductVO dto) {
        if (dto.getProductNo() == null) {
            productMapper.insert(dto);
        } else {
            productMapper.update(dto);
        }
    }

    public void delete(int id) {
        productMapper.delete(id);
    }
}