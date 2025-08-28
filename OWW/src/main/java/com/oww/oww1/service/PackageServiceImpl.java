package com.oww.oww1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.PackageCardVO;
import com.oww.oww1.mapper.PackageMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageMapper mapper;

    @Override
    public List<PackageCardVO> searchPackages(Integer type, String q, String region, Integer minBudget, Integer maxBudget, String sort) {
        Map<String,Object> params = new HashMap<>();
        if (type != null) params.put("type", type);
        params.put("q", (q == null) ? null : q.trim());
        params.put("region", (region == null) ? null : region.trim());
        // 예산(만원) → 원 단위
        if (minBudget != null) params.put("minPrice", minBudget * 10000);
        if (maxBudget != null) params.put("maxPrice", maxBudget * 10000);
        params.put("sort", (sort == null || sort.isBlank()) ? "pop" : sort);
        return mapper.search(params);
    }

    @Override
    public PackageCardVO getOne(int packageNo) {
        return mapper.findOne(packageNo);
    }
}
