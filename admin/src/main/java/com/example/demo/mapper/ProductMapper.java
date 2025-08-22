// src/main/java/com/example/demo/mapper/PackageMapper.java
package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.vo.PackageVO;
import com.example.demo.vo.ProductVO;

@Mapper
public interface ProductMapper {

    List<PackageVO> findAll(@Param("q") String q,
                            @Param("type") Integer type,
                            @Param("offset") int offset,
                            @Param("limit") int limit);

    int count(@Param("q") String q,
              @Param("type") Integer type);

    PackageVO findById(@Param("id") Long id);

    void insert(PackageVO vo);

    void update(PackageVO vo);

    void updateDiscount(@Param("id") Long id,
                        @Param("discount") int discount);

    void delete(@Param("id") Long id);

    // category = varchar(20)
    List<ProductVO> findProductsByCategory(@Param("category") String category);
}
