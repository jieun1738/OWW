// src/main/java/com/example/demo/mapper/PackageMapper.java
package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.vo.PackageVO;
import com.example.demo.vo.ProductVO;

@Mapper
public interface PackageMapper {

    List<PackageVO> findAll(@Param("q") String q,
                            @Param("type") Integer type,
                            @Param("offset") int offset,
                            @Param("limit") int limit);

    int count(@Param("q") String q,
              @Param("type") Integer type);

    PackageVO findByPackageNo(@Param("packageNo") Long packageNo);

    void insert(PackageVO vo);

    void update(PackageVO vo);

    void updateDiscount(@Param("packageNo") Long packageNo,
                        @Param("discount") int discount);

    void delete(@Param("packageNo") Long packageNo);

  
    List<ProductVO> findProductsByCategory(@Param("category") String category);
}
