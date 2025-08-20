package com.example.demo.mapper;

import com.example.demo.vo.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<ProductDto> findAll(@Param("q") String q,
                             @Param("category") Integer category,
                             @Param("offset") int offset,
                             @Param("limit") int limit);
    int count(@Param("q") String q, @Param("category") Integer category);

    ProductDto findById(@Param("id") int id);

    int insert(ProductDto dto);
    int update(ProductDto dto);
    int delete(@Param("id") int id);
}
