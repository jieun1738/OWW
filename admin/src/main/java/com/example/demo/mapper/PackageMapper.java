package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.PackageVO;

@Mapper
public interface PackageMapper {
    List<PackageVO> findAll();
    PackageVO findById(Long id);
    void insert(PackageVO vo);
    void update(PackageVO vo);
    void delete(Long id);
}
