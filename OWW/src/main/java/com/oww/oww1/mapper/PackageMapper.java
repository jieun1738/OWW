package com.oww.oww1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.oww.oww1.VO.PackageVO;

/** PACKAGE + 조인(표시용 파생필드 포함) 매퍼 */
@Mapper
public interface PackageMapper {
    List<PackageVO> listPackagesWithParts();

    PackageVO findPackageWithParts(@Param("packageNo") int packageNo);
}
