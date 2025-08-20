package com.example.demo.mapper;

import com.example.demo.vo.PackageAdminVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PackageAdminMapper {
    List<PackageAdminVO> findAll(@Param("q") String q,
                                 @Param("offset") int offset,
                                 @Param("limit") int limit);
    int count(@Param("q") String q);

    PackageAdminVO findById(@Param("id") int packageNo);
}
