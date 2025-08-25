package com.oww.oww1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.PackageAdminViewVO;
import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.mapper.PackageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {
    private final PackageMapper packageMapper;

    @Override
    public List<PackageAdminViewVO> findAllPackages() {
        return packageMapper.selectAllPackages();
    }

    @Override
    public List<PackageAdminViewVO> findByType(int type) {
        return packageMapper.selectByType(type);
    }

    @Override
    public PackageVO findPackageByNo(int packageNo) {
        return packageMapper.selectPackageByNo(packageNo);
    }
}
