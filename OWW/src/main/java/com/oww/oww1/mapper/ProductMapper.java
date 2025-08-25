package com.oww.oww1.mapper;

import java.util.List;

import com.oww.oww1.VO.ProductVO;

public interface ProductMapper {
    List<ProductVO> selectAll();
    List<ProductVO> selectByCategory(int category); // 0~3
    ProductVO selectByPk(int productNo);
}
