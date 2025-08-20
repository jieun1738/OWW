package com.example.demo.mapper;

import com.example.demo.vo.PackageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PackageMapper {
    PackageVO findById(@Param("id") int packageNo);
    int insert(PackageVO vo); // 트리거로 package_no 자동 채번이면 null 넘겨도 OK
    int update(PackageVO vo);
    int updateDiscount(@Param("id") int packageNo, @Param("discount") Integer discount);
    int delete(@Param("id") int packageNo);
}
