package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.oww.oww1.VO.ProductVO;

public interface ProductMapper {
    List<ProductVO> findAll(@Param("q") String q,
                            @Param("exPattern") String exPattern,
                            @Param("sort") String sort);

    List<ProductVO> findByCategory(@Param("cat") int cat,
                                   @Param("q") String q,
                                   @Param("exPattern") String exPattern,
                                   @Param("sort") String sort);

    ProductVO findByNo(@Param("productNo") int productNo);
}
