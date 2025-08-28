package com.oww.oww1.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.oww.oww1.VO.PackageCardVO;

@Mapper
public interface PackageMapper {
    List<PackageCardVO> search(Map<String,Object> params);
    PackageCardVO findOne(int packageNo);
}
