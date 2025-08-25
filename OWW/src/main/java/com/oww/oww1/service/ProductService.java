package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.ProductVO;

public interface ProductService {
    List<ProductVO> findAll();
    List<ProductVO> findByCategory(int category);
    ProductVO findByPk(int productNo);
}
