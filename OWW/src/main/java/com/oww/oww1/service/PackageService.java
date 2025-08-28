package com.oww.oww1.service;

import java.util.List;

import com.oww.oww1.VO.PackageCardVO;

public interface PackageService {
    List<PackageCardVO> searchPackages(Integer type, String q, String region, Integer minBudget, Integer maxBudget, String sort);
    PackageCardVO getOne(int packageNo);
}
