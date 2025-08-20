package com.wedding.oww.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wedding.oww.vo.ProductVO;

@Mapper
public interface ProductMapper {
    ProductVO findById(@Param("productNo") Long productNo);

    List<ProductVO> findForDIY(
            @Param("category") Integer category, // null이면 전체
            @Param("q") String q,                // null 허용
            @Param("sort") String sort           // cost_asc | cost_desc | null
    );
    
    // 다건 조회(IN), confirm
    List<ProductVO> findByIds(@Param("ids") List<Long> ids);
    
    List<ProductVO> search(
    		@Param("category") Integer category,
            @Param("q") String q,
            @Param("sort") String sort);

}
