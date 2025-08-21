package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.vo.PackageVO;
import com.example.demo.vo.ProductVO;

@Mapper
public interface PackageMapper {
    // 패키지 CRUD
    List<PackageVO> findAll();               // 목록 
    PackageVO findById(@Param("id") int id); // 상세 (뷰로 조회)
    int insert(PackageVO vo);                // package 테이블 insert
    int update(PackageVO vo);                // package 테이블 update
    int delete(@Param("id") int id);         // package 테이블 delete

    // 드롭다운 용: 카테고리별 상품
    List<ProductVO> findProductsByCategory(@Param("category") int category);
}
