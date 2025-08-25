package com.oww.oww1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    @Override
    public List<ProductVO> findAll() { return productMapper.selectAll(); }

    @Override
    public List<ProductVO> findByCategory(int category) { return productMapper.selectByCategory(category); }

    @Override
    public ProductVO findByPk(int productNo) { return productMapper.selectByPk(productNo); }
}
