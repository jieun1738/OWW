package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.ProductMapper;
import com.example.demo.vo.ProductVO;

@Service
public class ProductService {

    private final ProductMapper mapper;

    public ProductService(ProductMapper mapper) {
        this.mapper = mapper;
    }

    public List<ProductVO> findAll() {
        return mapper.findAll();
    }

    public ProductVO findById(Long id) {
        return mapper.findById(id);
    }

    @Transactional
    public void save(ProductVO product) {
        if (product.getId() == null) {
            mapper.insert(product);
        } else {
            mapper.update(product);
        }
    }

    public void delete(Long id) {
        mapper.delete(id);
    }
}
