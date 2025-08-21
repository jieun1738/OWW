package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.vo.ProductVO;

/**
 * product 테이블 CRUD + 검색/페이징 전용 Mapper
 * - category: 0=hall, 1=studio, 2=dress, 3=makeup
 */
@Mapper
public interface ProductMapper {

    /**
     * 목록 조회 (검색 + 카테고리 필터 + 페이징)
     * @param q        상품명/업체명(=product_name)에 대한 부분검색 키워드
     * @param category 카테고리 필터 
     * @param offset   시작 행(0-base)
     * @param limit    조회 
     */
    List<ProductVO> findAll(@Param("q") String q,
                            @Param("category") Integer category,
                            @Param("offset") int offset,
                            @Param("limit") int limit);


    int count(@Param("q") String q,
              @Param("category") Integer category);

    ProductVO findById(@Param("id") int id);

    int insert(ProductVO vo);

    int update(ProductVO vo);

    int delete(@Param("id") int id);
}
