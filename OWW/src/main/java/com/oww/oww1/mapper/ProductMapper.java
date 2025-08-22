// src/main/java/com/oww/oww1/mapper/ProductMapper.java
package com.oww.oww1.mapper;

import com.oww.oww1.VO.ProductVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * PRODUCT 테이블 매퍼
 * - 컨트롤러/서비스에서 사용하는 메서드명과 정확히 일치
 * - 숫자형은 원시형 int/long 사용
 */
@Mapper
public interface ProductMapper {

    List<ProductVO> findAll();

    ProductVO findById(long productNo);

    List<ProductVO> findByCategory(int category);

    List<ProductVO> findByIds(@Param("ids") List<Integer> ids);

    List<ProductVO> search(@Param("category") Integer category,
                           @Param("q") String q,
                           @Param("sort") String sort);
}
