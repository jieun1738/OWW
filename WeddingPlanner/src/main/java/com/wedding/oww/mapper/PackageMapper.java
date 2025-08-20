package com.wedding.oww.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.wedding.oww.vo.PackageVO;

@Mapper
public interface PackageMapper {
    PackageVO findById(@Param("packageNo") Long packageNo);

    List<PackageVO> findByType(@Param("type") Integer type); // null이면 전체
    
    List<PackageVO> findAllByType(@Param("type") Integer type);
}
