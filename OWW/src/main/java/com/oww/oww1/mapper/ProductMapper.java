package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oww.oww1.VO.ProductVO;

/** PRODUCT 조회 전용 매퍼 */
@Mapper
public interface ProductMapper {
    // 0: hall, 1: studio, 2: dress, 3: makeup
    List<ProductVO> findByCategory(@Param("category") int category);

    ProductVO findByNo(@Param("productNo") int productNo);
}
