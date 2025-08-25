package com.oww.oww1.mapper;

import java.util.List;

import com.oww.oww1.VO.PackageAdminViewVO;
import com.oww.oww1.VO.PackageVO;

public interface PackageMapper {
    List<PackageAdminViewVO> selectAllPackages(); // package_admin_v
    List<PackageAdminViewVO> selectByType(int type); // 0~4
    PackageVO selectPackageByNo(int packageNo); // package 원본
}
