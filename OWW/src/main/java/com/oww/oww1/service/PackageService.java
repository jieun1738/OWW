package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.PackageAdminViewVO;
import com.oww.oww1.VO.PackageVO;

public interface PackageService {
    List<PackageAdminViewVO> findAllPackages();
    List<PackageAdminViewVO> findByType(int type); // 0~4
    PackageVO findPackageByNo(int packageNo);
}
