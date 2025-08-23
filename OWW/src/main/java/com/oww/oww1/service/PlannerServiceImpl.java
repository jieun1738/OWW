package com.oww.oww1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oww.oww1.VO.PackageVO;
import com.oww.oww1.VO.PlanVO;
import com.oww.oww1.VO.ProductVO;
import com.oww.oww1.mapper.PackageMapper;
import com.oww.oww1.mapper.PlanMapper;
import com.oww.oww1.mapper.ProductMapper;

import lombok.RequiredArgsConstructor;

/**
 * 구현체(impl)
 * - MyBatis Mapper 호출만 담당 (비즈니스 로직 단순화)
 */
@Service
@RequiredArgsConstructor
public class PlannerServiceImpl implements PlannerService {

    private final ProductMapper productMapper;
    private final PackageMapper packageMapper;
    private final PlanMapper planMapper;

    @Override
    public List<ProductVO> findProductsByCategory(int category) {
        return productMapper.findByCategory(category);
    }

    @Override
    public List<PackageVO> listPackages() {
        return packageMapper.listPackagesWithParts();
    }

    @Override
    public PackageVO getPackage(int packageNo) {
        return packageMapper.findPackageWithParts(packageNo);
    }

    @Override
    public void confirmPlan(String userEmail, Integer packageNo, int hall, int studio, int dress, int makeup) {
        PlanVO vo = new PlanVO();
        vo.setUser_email(userEmail);
        vo.setPackage_no(packageNo != null ? packageNo : 9999);
        vo.setHall(hall);
        vo.setStudio(studio);
        vo.setDress(dress);
        vo.setMakeup(makeup);
        planMapper.insertPlan(vo);
    }

    @Override
    public List<PlanVO> getPlansByUser(String userEmail) {
        return planMapper.findByUser(userEmail);
    }
}
