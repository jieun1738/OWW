package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.oww.oww1.vo.ProductVO;

@Mapper
public interface ProductMapper {

    // 상품 전체 조회
    List<ProductVO> findAll();

    // 상품 단건 조회
    ProductVO findById(Long id);

    // 상품 등록
    int insert(ProductVO product);

    // 상품 수정
    int update(ProductVO product);

    // 상품 삭제
    int delete(Long id);
}
